package com.et.tf.service;

import jakarta.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.NdArrays;
import org.tensorflow.ndarray.Shape;
import org.tensorflow.ndarray.buffer.FloatDataBuffer;
import org.tensorflow.op.OpScope;
import org.tensorflow.op.Scope;
import org.tensorflow.proto.framework.DataType;
import org.tensorflow.types.TFloat32;
import org.tensorflow.types.TInt32;
import org.tensorflow.types.TString;
import org.tensorflow.types.family.TType;

//Inspired from https://github.com/tensorflow/tensorflow/blob/master/tensorflow/java/src/main/java/org/tensorflow/examples/LabelImage.java
@Service
@Slf4j
public class ClassifyImageService {

    private final Session session;
    private final List<String> labels;
    private final String outputLayer;

    private final int W;
    private final int H;
    private final float mean;
    private final float scale;

    public ClassifyImageService(
        Graph inceptionGraph, List<String> labels, @Value("${tf.outputLayer}") String outputLayer,
        @Value("${tf.image.width}") int imageW, @Value("${tf.image.height}") int imageH,
        @Value("${tf.image.mean}") float mean, @Value("${tf.image.scale}") float scale
    ) {
        this.labels = labels;
        this.outputLayer = outputLayer;
        this.H = imageH;
        this.W = imageW;
        this.mean = mean;
        this.scale = scale;
        this.session = new Session(inceptionGraph);
    }

    public LabelWithProbability classifyImage(byte[] imageBytes) {
        long start = System.currentTimeMillis();
        try (Tensor image = normalizedImageToTensor(imageBytes)) {
            float[] labelProbabilities = classifyImageProbabilities(image);
            int bestLabelIdx = maxIndex(labelProbabilities);
            LabelWithProbability labelWithProbability =
                new LabelWithProbability(labels.get(bestLabelIdx), labelProbabilities[bestLabelIdx] * 100f, System.currentTimeMillis() - start);
            log.debug(String.format(
                    "Image classification [%s %.2f%%] took %d ms",
                    labelWithProbability.getLabel(),
                    labelWithProbability.getProbability(),
                    labelWithProbability.getElapsed()
                )
            );
            return labelWithProbability;
        }
    }

    private float[] classifyImageProbabilities(Tensor image) {
        try (Tensor result = session.runner().feed("input", image).fetch(outputLayer).run().get(0)) {
            final Shape resultShape = result.shape();
            final long[] rShape = resultShape.asArray();
            if (resultShape.numDimensions() != 2 || rShape[0] != 1) {
                throw new RuntimeException(
                    String.format(
                        "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
                        Arrays.toString(rShape)
                    ));
            }
            int nlabels = (int) rShape[1];
            FloatDataBuffer resultFloatBuffer = result.asRawTensor().data().asFloats();
            float[] dst = new float[nlabels];
            resultFloatBuffer.read(dst);
            return dst;
        }
    }

    private int maxIndex(float[] probabilities) {
        int best = 0;
        for (int i = 1; i < probabilities.length; ++i) {
            if (probabilities[i] > probabilities[best]) {
                best = i;
            }
        }
        return best;
    }

    private Tensor normalizedImageToTensor(byte[] imageBytes) {
        try (Graph g = new Graph();
             TInt32 batchTensor = TInt32.scalarOf(0);
             TInt32 sizeTensor = TInt32.vectorOf(H, W);
             TFloat32 meanTensor = TFloat32.scalarOf(mean);
             TFloat32 scaleTensor = TFloat32.scalarOf(scale);
        ) {
            GraphBuilder b = new GraphBuilder(g);
            //Tutorial python here: https://github.com/tensorflow/tensorflow/tree/master/tensorflow/examples/label_image
            // Some constants specific to the pre-trained model at:
            // https://storage.googleapis.com/download.tensorflow.org/models/inception_v3_2016_08_28_frozen.pb.tar.gz
            //
            // - The model was trained with images scaled to 299x299 pixels.
            // - The colors, represented as R, G, B in 1-byte each were converted to
            //   float using (value - Mean)/Scale.

            // Since the graph is being constructed once per execution here, we can use a constant for the
            // input image. If the graph were to be re-used for multiple input images, a placeholder would
            // have been more appropriate.
            final Output input = b.constant("input", TString.tensorOfBytes(NdArrays.scalarOfObject(imageBytes)));
            final Output output =
                b.div(
                    b.sub(
                        b.resizeBilinear(
                            b.expandDims(
                                b.cast(b.decodeJpeg(input, 3), DataType.DT_FLOAT),
                                b.constant("make_batch", batchTensor)
                            ),
                            b.constant("size", sizeTensor)
                        ),
                        b.constant("mean", meanTensor)
                    ),
                    b.constant("scale", scaleTensor)
                );
            try (Session s = new Session(g)) {
                return s.runner().fetch(output.op().name()).run().get(0);
            }
        }
    }

    static class GraphBuilder {
        final Scope scope;

        GraphBuilder(Graph g) {
            this.g = g;
            this.scope = new OpScope(g);
        }

        Output div(Output x, Output y) {
            return binaryOp("Div", x, y);
        }

        Output sub(Output x, Output y) {
            return binaryOp("Sub", x, y);
        }

        Output resizeBilinear(Output images, Output size) {
            return binaryOp("ResizeBilinear", images, size);
        }

        Output expandDims(Output input, Output dim) {
            return binaryOp("ExpandDims", input, dim);
        }

        Output cast(Output value, DataType dtype) {
            return g.opBuilder("Cast", "Cast", scope).addInput(value).setAttr("DstT", dtype).build().output(0);
        }

        Output decodeJpeg(Output contents, long channels) {
            return g.opBuilder("DecodeJpeg", "DecodeJpeg", scope)
                .addInput(contents)
                .setAttr("channels", channels)
                .build()
                .output(0);
        }

        Output<? extends TType> constant(String name, Tensor t) {
            return g.opBuilder("Const", name, scope)
                .setAttr("dtype", t.dataType())
                .setAttr("value", t)
                .build()
                .output(0);
        }

        private Output binaryOp(String type, Output in1, Output in2) {
            return g.opBuilder(type, type, scope).addInput(in1).addInput(in2).build().output(0);
        }

        private final Graph g;
    }

    @PreDestroy
    public void close() {
        session.close();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LabelWithProbability {
        private String label;
        private float probability;
        private long elapsed;
    }
}

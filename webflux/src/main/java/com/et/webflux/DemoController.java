package com.et.webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;


    @GetMapping("/monoTest")
    public Mono<Object> monoTest() {
        // method one
//        String data = getOneResult("monoTest()");
//        return Mono.just(data);

        // method two
        return Mono.create(cityMonoSink -> {
            String data = demoService.getOneResult("monoTest()");
            cityMonoSink.success(data);
        });
    }


    @GetMapping("/fluxTest")
    public Flux<Object> fluxTest() {
        // method one
//        List<String> list = getMultiResult("fluxTest()");
//        return Flux.fromIterable(list);

        // method two
        return Flux.fromIterable(demoService.getMultiResult("fluxTest()"));
    }

}

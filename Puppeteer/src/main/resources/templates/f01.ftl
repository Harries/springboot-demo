<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>freemarker</title>
    <script src="https://cdn.jsdelivr.net/npm/jsbarcode@3.11.5/dist/JsBarcode.all.min.js"></script>
</head>
<body style="font-size: 20px;">
<div style="display: flex;
        align-items: flex-start;
        border: 1px solid darkred;
        box-shadow: 1px 2px 1px 5px #cccccc;"
>
    <div style="display:flex;flex-shrink: 0;width:100px;">words:</div> <h1>${msg}</h1>
</div>
<div style="display: flex;align-items: center;margin: 10px 0px;">
    <div>image: </div><img style="width: 150px;height: 150px;" src="${img}" />
</div>
<#--<div style="display: flex;align-items: flex-end;">-->
barcode: <svg id="barcode"></svg>
<#--</div>-->
</body>
<#--load js-->
<script>
    let e = document.getElementById("barcode");
    JsBarcode(e, "Hi world!");
</script>
</html>
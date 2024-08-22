// index.js
const puppeteer = require('puppeteer');
async function test () {
    const browser = await puppeteer.launch();
    const page = await browser.newPage();
    await page.setViewport({
        width: 960,
        height: 760,
        deviceScaleFactor: 1,
    });

    // await page.setContent(imgHTML);

    await page.goto('http://localhost:8088/ftest');

    await page.evaluate(() => {
        let e = document.getElementById("barcode");
        JsBarcode(e, "Hi world!");
    });

    await page.screenshot({path: "./example.png"});
    await browser.close();
}
test()
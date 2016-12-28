fs = require('fs')
pug = require("pug")

hello = pug.renderFile('hello.pug')
fs.writeFile("../hello.html", hello)


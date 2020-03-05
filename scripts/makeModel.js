const cmm = require("./cmm");

const mapName = function(str) {
    return str.replace(/\s+/g, "_").toLowerCase();
};

const writeToFile = function(fileName, content) {
    require("fs").writeFile(fileName, content, function(err) {
        if (err) {
            console.error("Crap happens");
        }
    });
};

const appendRowToFile = function(fileName, row) {
    require("fs").appendFile(fileName, row, function(err) {
        if (err) {
            console.error("Crap happens");
        }
    });
};

const carMakeModelObj = (function(cmm) {
    const cmmArray = [];
    const cmmSQL = [];
    const modelSQL = [];

    let modelId = 0;
    let makeId = 0;

    cmm.forEach((row, rowIndex, matrix) => {
        const make = row[0];

        const makeObj = {};
        makeObj["id"] = ++makeId;
        makeObj["name"] = make;
        makeObj["models"] = [];

        // const insertRow = `insert into makers (id, name) values (${makeObj.id}, '${makeObj.name}');`;
        const insertRow = `insert into makers (id, name) values (${makeObj.id}, '${makeObj.name}');`;
        cmmSQL.push(insertRow);

        row.slice(2).forEach((model, index) => {
            const modelObj = {};
            modelObj["id"] = ++modelId;
            modelObj["name"] = model;
            makeObj.models.push(modelObj);

            // const insertRow = `insert into models (id, name, maker_id, maker_name) values (${modelObj.id}, '${modelObj.name}', ${makeObj.id},' ${make}');`;
            const insertRow = `insert into models (id, name, maker_id) values (${modelObj.id}, '${modelObj.name}', ${makeObj.id});`;
            modelSQL.push(insertRow);
        });

        cmmArray.push(makeObj);
    });

    const newArray = cmmSQL.concat(modelSQL);

    return {
        cmmArray,
        newArray
    };
})(cmm);

(function main() {
    writeToFile("./data.sql", "");
    appendRowToFile("./data.sql", carMakeModelObj.newArray.join("\n"));
})();

// const equipments = require("./equipment.json").equipments;
const cmm = require("./cmm");
const towns = require("./bg_cities").data;
const dbConnection = require("./dbConnection");
// const makeModelsAutoList = require("./make_models_autolist");
// const autoscout = require("./autoscout-obj");
// const cardatabase = require("./cardatabase");

const API_PREFIX = "auto_";

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
    const insertRow = `insert into ${API_PREFIX}makers (id, name) values (${makeObj.id}, '${makeObj.name}');`;
    cmmSQL.push(insertRow);

    row.slice(2).forEach((model, index) => {
      const modelObj = {};
      modelObj["id"] = ++modelId;
      modelObj["name"] = model;
      makeObj.models.push(modelObj);

      // const insertRow = `insert into models (id, name, maker_id, maker_name) values (${modelObj.id}, '${modelObj.name}', ${makeObj.id},' ${make}');`;
      const insertRow = `insert into ${API_PREFIX}models (id, name, maker_id) values (${modelObj.id}, '${modelObj.name}', ${makeObj.id});`;
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

const townsObj = (function(townsArray) {
  const townsObjArray = [];
  const townsSQL = [];

  townsArray.forEach((town, idx) => {
    const index = idx + 1;

    const townObj = {};

    townObj["id"] = index;
    townObj["name"] = town.asciiname;
    townsObjArray.push(townObj);

    const insertRow = `insert into ${API_PREFIX}locations (id, name, maps_url) values (${index}, '${town.asciiname}', '${town.location}');`;
    // const insertRow = `insert into locations (id, name) values (${index}, '${town.asciiname}');`;
    townsSQL.push(insertRow);
  });

  return {
    townsObjArray,
    townsSQL
  };
})(towns);

async function executeQuery(query) {
  let con = await dbConnection();
  try {
    await con.query("START TRANSACTION");
    await con.query(query);
    await con.query("COMMIT");
    console.log(query);
  } catch (ex) {
    await con.query("ROLLBACK");
    console.log(ex);
    throw ex;
  } finally {
    await con.release();
    await con.destroy();
  }
}

const http = require("./requester").http;

const baseUrl = "http://localhost:8007/api/vehicles";

(async function main() {
  // const generateRoleInsert = require("./generateRoleData");
  // const rolesSql = await generateRoleInsert();

  // const state = await http.get(baseUrl);

  let entitiesArray = [
    carMakeModelObj.newArray,
    townsObj.townsSQL
    // rolesSql
  ];

  const insert = entitiesArray.reduce((a, b) => {
    return a.concat(b);
  }, []);

  await executeQuery(insert.join("\n"));

  const response = await http.get(baseUrl + "/state");

  const state = response.data;
  const metadata = state.data.metadata;

  const generateVehiclesInsert = require("./generateVehicleData");
  const generateEngineSqlInsert = require("./generateEngineData");
  const generateVehiclesFeaturesSqlInserts = require("./generateVehicleFeaturesData");
  const generateOfferSqlInsert = require("./generateOfferData");
  const generateUserSqlInsert = require("./generateUserData");
  // const generateImageSqlInset = require("./generateImageData");
  // const generateUserRoleSqlInset = require("./generateUserRoleData");

  const vehiclesInsertSql = await generateVehiclesInsert(
    metadata,
    state.data.makers
  );
  const enginesSql = await generateEngineSqlInsert(metadata);
  const vehiclesFeaturesSql = await generateVehiclesFeaturesSqlInserts(
    metadata
  );
  const usersSql = await generateUserSqlInsert(metadata.seller);
  const offerSql = await generateOfferSqlInsert();
  // const imageSql = await generateImageSqlInset();
  // const userRoleSql = await generateUserRoleSqlInset();
  let a = 5;

  const sql = [
    usersSql,
    offerSql,
    vehiclesInsertSql,
    vehiclesFeaturesSql,
    enginesSql
    //     imageSql,
    //     userRoleSql
  ];

  const dependentInserts = sql.reduce((a, b) => {
    return a.concat(b);
  }, []);

  writeToFile("./data.sql", "");
  appendRowToFile("./data.sql", insert.join("\n"));
  appendRowToFile("./data.sql", dependentInserts.join("\n"));

  // writeToFile("./data-next.sql", "");
  // appendRowToFile("./data-next.sql", dependentInserts.join("\n"));

  await executeQuery(dependentInserts.join("\n"));
})();

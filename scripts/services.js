const utils = require("./utils");
const api = require("./api");

const makerService = (() => {
  async function loadAllMakers() {
    console.log(api.makersAll());
    return await utils.httpGET(api.makersAll());
  }

  async function getMakerById(makeId) {
    return await utils.httpGET(api.makerById(makeId));
  }

  return {
    loadAllMakers,
    getMakerById
  };
})();

const fuelTypeService = (() => {
  async function loadAllFuelTypes() {
    return await utils.httpGET(api.fuelTypesAll());
  }

  return {
    loadAllFuelTypes
  };
})();

const powerService = (() => {
  const powerSteps = [];

  for (let i = 50; i <= 1000; i += 25) {
    powerSteps.push({
      id: i,
      name: `${i}`
    });
  }

  return {
    powerSteps,
    powerUnits: ["hp", "kW"],
    getRandomPowerOutput: () => {
      return utils.getRandomInt(50, 550);
    }
  };
})();

const euroStandardService = (() => {
  async function getAllEuroStandards() {
    return await utils.httpGET(api.euroStandardAll());
  }

  return {
    getAllEuroStandards
  };
})();

const cylinderService = (() => {
  const cylindersCount = [2, 3, 4, 6, 8, 12];
  return {
    cylindersCount
  };
})();

const featureService = (() => {
  async function loadAllFeatures() {
    return await utils.httpGET(api.featuresAll());
  }

  return {
    loadAllFeatures
  };
})();

const bodyStyleService = (() => {
  async function loadAllBodyStyles() {
    return await utils.httpGET(api.bodyStylesAll());
  }

  return {
    loadAllBodyStyles
  };
})();
const driveService = (() => {
  async function loadAllDrives() {
    return await utils.httpGET(api.drivetrainAll());
  }

  return {
    loadAllDrives
  };
})();

const colorService = (() => {
  async function loadAllColors() {
    return await utils.httpGET(api.colorsAll());
  }

  return {
    loadAllColors
  };
})();

const gearsService = (() => {
  async function loadAllGears() {
    return await utils.httpGET(api.gearsAll());
  }

  return {
    loadAllGears
  };
})();

const locationService = (() => {
  async function getAllLocations() {
    return await utils.httpGET(api.locationsAll());
  }

  return {
    getAllLocations
  };
})();

const API_PREFIX = "auto_";
const BASE_URL = "http://localhost:8007/api/vehicles";

module.exports = {
  fuelTypeService,
  powerService,
  euroStandardService,
  cylinderService,
  featureService,
  makerService,
  bodyStyleService,
  driveService,
  colorService,
  gearsService,
  locationService,
  API_PREFIX,
  BASE_URL
};

const equipments = require("./equipment.json").equipments;

const toEnum = array => {
    return array.map(element => {
        const textValue = element;
        const enumValue = element
            .split(/[-\s,]+/)
            .map(word => word.toUpperCase())
            .join("_");

        console.log(`${enumValue}("${textValue}"),`);
    });
};

const colors = [
    "Black",
    "Gray",
    "Cream",
    "Light Brown",
    "Dark Brown",
    "Dark Red",
    "Red",
    "Dark Blue",
    "Light Blue",
    "White",
    "Orange",
    "Silver",
    "Gold"
];

const allUpholsteryIds = [
    {
        id: "AL",
        name: "alcantara"
    },
    {
        id: "CL",
        name: "Cloth"
    },
    {
        id: "FL",
        name: "Full leather"
    },
    {
        id: "PL",
        name: "Part leather"
    },
    {
        id: "VL",
        name: "Velour"
    },
    {
        id: "OT",
        name: "Other"
    }
].map(el => el.name);

toEnum(allUpholsteryIds);

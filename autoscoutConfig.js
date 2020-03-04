const obj = {
  initialState: {
    aiSearch: {
      showAiSearch: false,
      aiSearchText: ""
    },
    filterChanges: 0,
    filters: {
      sorting: "PriceAscending",
      vehicleType: "car",
      vat: null,
      numberOfAxles: null,
      xborder: null,
      paintWorks: [],
      makeModelModellineVersions: [],
      bodyTypes: [],
      fuelTypes: [],
      gears: [],
      usageState: ["N", "U"],
      upholsteries: [],
      onlineSinceId: null,
      firstRegistrationYear: {
        from: null,
        to: null
      },
      price: {
        from: null,
        to: null
      },
      financeRate: {
        from: null,
        to: null
      },
      priceOrFinanceRateSelection: "price",
      mileage: {
        from: null,
        to: null
      },
      seats: {
        from: null,
        to: null
      },
      beds: {
        from: null,
        to: null
      },
      emissionClass: null,
      emissionSticker: null,
      previousOwner: null,
      power: {
        type: "kw",
        from: null,
        to: null
      },
      country: "",
      area: {
        lastSuggestion: null,
        lat: null,
        lon: null
      },
      zipRadius: null,
      priceEvaluation: [],
      offerTypes: ["N", "J", "U", "O", "D", "S"],
      doors: "All",
      seller: null,
      equipments: [],
      bodyColors: [],
      interiorColors: [],
      seals: [],
      currentPage: null,
      dealerId: null,
      otTransactable: null,
      otOneSideContract: null,
      grossWeight: {
        from: null,
        to: null
      },
      newDriverSuitable: null,
      consumption: {
        from: null,
        to: null
      }
    },
    areaSuggestions: {
      searchedTerm: null,
      suggestionsForTerm: []
    },
    taxonomy: {
      allMakes: [
        {
          id: "9",
          label: "Audi",
          top: true
        },
        {
          id: "13",
          label: "BMW",
          top: true
        },
        {
          id: "29",
          label: "Ford",
          top: true
        },
        {
          id: "47",
          label: "Mercedes-Benz",
          top: true
        },
        {
          id: "54",
          label: "Opel",
          top: true
        },
        {
          id: "74",
          label: "Volkswagen",
          top: true
        },
        {
          id: "60",
          label: "Renault",
          top: true
        },
        {
          id: "51539",
          label: "9ff",
          top: false
        },
        {
          id: "16396",
          label: "Abarth",
          top: false
        },
        {
          id: "14979",
          label: "AC",
          top: false
        },
        {
          id: "16429",
          label: "ACM",
          top: false
        },
        {
          id: "16356",
          label: "Acura",
          top: false
        },
        {
          id: "16352",
          label: "Aixam",
          top: false
        },
        {
          id: "6",
          label: "Alfa Romeo",
          top: false
        },
        {
          id: "14",
          label: "Alpina",
          top: false
        },
        {
          id: "51796",
          label: "Alpine",
          top: false
        },
        {
          id: "51545",
          label: "Amphicar",
          top: false
        },
        {
          id: "16419",
          label: "Ariel Motor",
          top: false
        },
        {
          id: "16427",
          label: "Artega",
          top: false
        },
        {
          id: "16431",
          label: "Aspid",
          top: false
        },
        {
          id: "8",
          label: "Aston Martin",
          top: false
        },
        {
          id: "15643",
          label: "Austin",
          top: false
        },
        {
          id: "15644",
          label: "Autobianchi",
          top: false
        },
        {
          id: "16437",
          label: "Auverland",
          top: false
        },
        {
          id: "51774",
          label: "Baic",
          top: false
        },
        {
          id: "16400",
          label: "Bedford",
          top: false
        },
        {
          id: "16416",
          label: "Bellier",
          top: false
        },
        {
          id: "11",
          label: "Bentley",
          top: false
        },
        {
          id: "16418",
          label: "Bolloré",
          top: false
        },
        {
          id: "16424",
          label: "Borgward",
          top: false
        },
        {
          id: "16367",
          label: "Brilliance",
          top: false
        },
        {
          id: "15",
          label: "Bugatti",
          top: false
        },
        {
          id: "16",
          label: "Buick",
          top: false
        },
        {
          id: "16379",
          label: "BYD",
          top: false
        },
        {
          id: "17",
          label: "Cadillac",
          top: false
        },
        {
          id: "15672",
          label: "Caravans-Wohnm",
          top: false
        },
        {
          id: "16407",
          label: "Casalini",
          top: false
        },
        {
          id: "16335",
          label: "Caterham",
          top: false
        },
        {
          id: "16401",
          label: "Changhe",
          top: false
        },
        {
          id: "16357",
          label: "Chatenet",
          top: false
        },
        {
          id: "16384",
          label: "Chery",
          top: false
        },
        {
          id: "19",
          label: "Chevrolet",
          top: false
        },
        {
          id: "20",
          label: "Chrysler",
          top: false
        },
        {
          id: "21",
          label: "Citroen",
          top: false
        },
        {
          id: "16411",
          label: "CityEL",
          top: false
        },
        {
          id: "16406",
          label: "CMC",
          top: false
        },
        {
          id: "16380",
          label: "Corvette",
          top: false
        },
        {
          id: "51802",
          label: "Cupra",
          top: false
        },
        {
          id: "16360",
          label: "Dacia",
          top: false
        },
        {
          id: "22",
          label: "Daewoo",
          top: false
        },
        {
          id: "16333",
          label: "DAF",
          top: false
        },
        {
          id: "23",
          label: "Daihatsu",
          top: false
        },
        {
          id: "16397",
          label: "Daimler",
          top: false
        },
        {
          id: "16434",
          label: "Dangel",
          top: false
        },
        {
          id: "16423",
          label: "De la Chapelle",
          top: false
        },
        {
          id: "51779",
          label: "De Tomaso",
          top: false
        },
        {
          id: "16391",
          label: "Derways",
          top: false
        },
        {
          id: "51773",
          label: "DFSK",
          top: false
        },
        {
          id: "2152",
          label: "Dodge",
          top: false
        },
        {
          id: "16339",
          label: "Donkervoort",
          top: false
        },
        {
          id: "16383",
          label: "DR Motor",
          top: false
        },
        {
          id: "16415",
          label: "DS Automobiles",
          top: false
        },
        {
          id: "51552",
          label: "Dutton",
          top: false
        },
        {
          id: "51794",
          label: "e.GO",
          top: false
        },
        {
          id: "16436",
          label: "Estrima",
          top: false
        },
        {
          id: "27",
          label: "Ferrari",
          top: false
        },
        {
          id: "28",
          label: "Fiat",
          top: false
        },
        {
          id: "51543",
          label: "FISKER",
          top: false
        },
        {
          id: "51542",
          label: "Gac Gonow",
          top: false
        },
        {
          id: "16337",
          label: "Galloper",
          top: false
        },
        {
          id: "16386",
          label: "GAZ",
          top: false
        },
        {
          id: "16403",
          label: "GEM",
          top: false
        },
        {
          id: "51540",
          label: "GEMBALLA",
          top: false
        },
        {
          id: "51791",
          label: "Gillet",
          top: false
        },
        {
          id: "16421",
          label: "Giotti Victoria",
          top: false
        },
        {
          id: "2153",
          label: "GMC",
          top: false
        },
        {
          id: "51813",
          label: "Goupil",
          top: false
        },
        {
          id: "16382",
          label: "Great Wall",
          top: false
        },
        {
          id: "16409",
          label: "Grecav",
          top: false
        },
        {
          id: "51512",
          label: "Haima",
          top: false
        },
        {
          id: "51534",
          label: "Hamann",
          top: false
        },
        {
          id: "51816",
          label: "Haval",
          top: false
        },
        {
          id: "31",
          label: "Honda",
          top: false
        },
        {
          id: "15674",
          label: "HUMMER",
          top: false
        },
        {
          id: "51767",
          label: "Hurtan",
          top: false
        },
        {
          id: "33",
          label: "Hyundai",
          top: false
        },
        {
          id: "16355",
          label: "Infiniti",
          top: false
        },
        {
          id: "15629",
          label: "Innocenti",
          top: false
        },
        {
          id: "16402",
          label: "Iso Rivolta",
          top: false
        },
        {
          id: "35",
          label: "Isuzu",
          top: false
        },
        {
          id: "14882",
          label: "Iveco",
          top: false
        },
        {
          id: "16387",
          label: "IZH",
          top: false
        },
        {
          id: "37",
          label: "Jaguar",
          top: false
        },
        {
          id: "38",
          label: "Jeep",
          top: false
        },
        {
          id: "39",
          label: "Kia",
          top: false
        },
        {
          id: "51781",
          label: "Koenigsegg",
          top: false
        },
        {
          id: "50060",
          label: "KTM",
          top: false
        },
        {
          id: "40",
          label: "Lada",
          top: false
        },
        {
          id: "41",
          label: "Lamborghini",
          top: false
        },
        {
          id: "42",
          label: "Lancia",
          top: false
        },
        {
          id: "15641",
          label: "Land Rover",
          top: false
        },
        {
          id: "16426",
          label: "LDV",
          top: false
        },
        {
          id: "43",
          label: "Lexus",
          top: false
        },
        {
          id: "16393",
          label: "Lifan",
          top: false
        },
        {
          id: "16353",
          label: "Ligier",
          top: false
        },
        {
          id: "14890",
          label: "Lincoln",
          top: false
        },
        {
          id: "44",
          label: "Lotus",
          top: false
        },
        {
          id: "16359",
          label: "Mahindra",
          top: false
        },
        {
          id: "51780",
          label: "MAN",
          top: false
        },
        {
          id: "16435",
          label: "Mansory",
          top: false
        },
        {
          id: "16410",
          label: "Martin Motors",
          top: false
        },
        {
          id: "45",
          label: "Maserati",
          top: false
        },
        {
          id: "51803",
          label: "Maxus",
          top: false
        },
        {
          id: "16348",
          label: "Maybach",
          top: false
        },
        {
          id: "46",
          label: "Mazda",
          top: false
        },
        {
          id: "51519",
          label: "McLaren",
          top: false
        },
        {
          id: "16399",
          label: "Melex",
          top: false
        },
        {
          id: "48",
          label: "MG",
          top: false
        },
        {
          id: "16361",
          label: "Microcar",
          top: false
        },
        {
          id: "51766",
          label: "Minauto",
          top: false
        },
        {
          id: "16338",
          label: "MINI",
          top: false
        },
        {
          id: "50",
          label: "Mitsubishi",
          top: false
        },
        {
          id: "51782",
          label: "Mitsuoka",
          top: false
        },
        {
          id: "51",
          label: "Morgan",
          top: false
        },
        {
          id: "16388",
          label: "Moskvich",
          top: false
        },
        {
          id: "51554",
          label: "MP Lafer",
          top: false
        },
        {
          id: "51788",
          label: "MPM Motors",
          top: false
        },
        {
          id: "52",
          label: "Nissan",
          top: false
        },
        {
          id: "53",
          label: "Oldsmobile",
          top: false
        },
        {
          id: "15670",
          label: "Oldtimer",
          top: false
        },
        {
          id: "16341",
          label: "Pagani",
          top: false
        },
        {
          id: "51553",
          label: "Panther Westwinds",
          top: false
        },
        {
          id: "55",
          label: "Peugeot",
          top: false
        },
        {
          id: "50083",
          label: "PGO",
          top: false
        },
        {
          id: "16350",
          label: "Piaggio",
          top: false
        },
        {
          id: "51770",
          label: "Plymouth",
          top: false
        },
        {
          id: "51817",
          label: "Polestar",
          top: false
        },
        {
          id: "56",
          label: "Pontiac",
          top: false
        },
        {
          id: "57",
          label: "Porsche",
          top: false
        },
        {
          id: "15636",
          label: "Proton",
          top: false
        },
        {
          id: "15646",
          label: "Puch",
          top: false
        },
        {
          id: "16412",
          label: "Qoros",
          top: false
        },
        {
          id: "16425",
          label: "Qvale",
          top: false
        },
        {
          id: "51793",
          label: "RAM",
          top: false
        },
        {
          id: "51812",
          label: "Regis",
          top: false
        },
        {
          id: "16398",
          label: "Reliant",
          top: false
        },
        {
          id: "61",
          label: "Rolls-Royce",
          top: false
        },
        {
          id: "62",
          label: "Rover",
          top: false
        },
        {
          id: "51536",
          label: "Ruf",
          top: false
        },
        {
          id: "63",
          label: "Saab",
          top: false
        },
        {
          id: "16369",
          label: "Santana",
          top: false
        },
        {
          id: "64",
          label: "SEAT",
          top: false
        },
        {
          id: "51800",
          label: "Shuanghuan",
          top: false
        },
        {
          id: "65",
          label: "Skoda",
          top: false
        },
        {
          id: "15525",
          label: "smart",
          top: false
        },
        {
          id: "51538",
          label: "SpeedArt",
          top: false
        },
        {
          id: "16377",
          label: "Spyker",
          top: false
        },
        {
          id: "66",
          label: "SsangYong",
          top: false
        },
        {
          id: "51795",
          label: "StreetScooter",
          top: false
        },
        {
          id: "67",
          label: "Subaru",
          top: false
        },
        {
          id: "68",
          label: "Suzuki",
          top: false
        },
        {
          id: "51551",
          label: "Talbot",
          top: false
        },
        {
          id: "16404",
          label: "Tasso",
          top: false
        },
        {
          id: "16327",
          label: "Tata",
          top: false
        },
        {
          id: "51557",
          label: "Tazzari EV",
          top: false
        },
        {
          id: "51535",
          label: "TECHART",
          top: false
        },
        {
          id: "51520",
          label: "Tesla",
          top: false
        },
        {
          id: "16420",
          label: "Town Life",
          top: false
        },
        {
          id: "70",
          label: "Toyota",
          top: false
        },
        {
          id: "15633",
          label: "Trabant",
          top: false
        },
        {
          id: "16326",
          label: "Trailer-Anhänger",
          top: false
        },
        {
          id: "2120",
          label: "Triumph",
          top: false
        },
        {
          id: "16253",
          label: "Trucks-Lkw",
          top: false
        },
        {
          id: "71",
          label: "TVR",
          top: false
        },
        {
          id: "16389",
          label: "UAZ",
          top: false
        },
        {
          id: "51809",
          label: "Vanderhall",
          top: false
        },
        {
          id: "16385",
          label: "VAZ",
          top: false
        },
        {
          id: "16422",
          label: "VEM",
          top: false
        },
        {
          id: "73",
          label: "Volvo",
          top: false
        },
        {
          id: "16336",
          label: "Wartburg",
          top: false
        },
        {
          id: "51513",
          label: "Westfield",
          top: false
        },
        {
          id: "16351",
          label: "Wiesmann",
          top: false
        },
        {
          id: "16408",
          label: "Zastava",
          top: false
        },
        {
          id: "16394",
          label: "ZAZ",
          top: false
        },
        {
          id: "51798",
          label: "Zhidou",
          top: false
        },
        {
          id: "51807",
          label: "Zotye",
          top: false
        },
        {
          id: "16328",
          label: "Others",
          top: false
        }
      ],
      allBodyTypeIds: [
        {
          id: "1",
          name: "Compact"
        },
        {
          id: "2",
          name: "Convertible"
        },
        {
          id: "3",
          name: "Coupe"
        },
        {
          id: "4",
          name: "Off-Road/Pick-up"
        },
        {
          id: "6",
          name: "Sedans"
        },
        {
          id: "5",
          name: "Station wagon"
        },
        {
          id: "13",
          name: "Transporter"
        },
        {
          id: "12",
          name: "Van"
        },
        {
          id: "7",
          name: "Other"
        }
      ],
      priceSteps: [
        {
          id: 500,
          name: "€500"
        },
        {
          id: 1000,
          name: "€1,000"
        },
        {
          id: 1500,
          name: "€1,500"
        },
        {
          id: 2000,
          name: "€2,000"
        },
        {
          id: 2500,
          name: "€2,500"
        },
        {
          id: 3000,
          name: "€3,000"
        },
        {
          id: 4000,
          name: "€4,000"
        },
        {
          id: 5000,
          name: "€5,000"
        },
        {
          id: 6000,
          name: "€6,000"
        },
        {
          id: 7000,
          name: "€7,000"
        },
        {
          id: 8000,
          name: "€8,000"
        },
        {
          id: 9000,
          name: "€9,000"
        },
        {
          id: 10000,
          name: "€10,000"
        },
        {
          id: 12500,
          name: "€12,500"
        },
        {
          id: 15000,
          name: "€15,000"
        },
        {
          id: 17500,
          name: "€17,500"
        },
        {
          id: 20000,
          name: "€20,000"
        },
        {
          id: 25000,
          name: "€25,000"
        },
        {
          id: 30000,
          name: "€30,000"
        },
        {
          id: 40000,
          name: "€40,000"
        },
        {
          id: 50000,
          name: "€50,000"
        },
        {
          id: 75000,
          name: "€75,000"
        },
        {
          id: 100000,
          name: "€100,000"
        }
      ]
    }
  },
  language: "en",
  locale: "en-GB",
  isUserLoggedIn: false,
  translations: {
    location: {
      countries: {
        luxembourg: "Luxembourg",
        germany: "Germany",
        belgium: "Belgium",
        all: "Europe",
        italy: "Italy",
        netherlands: "Netherlands",
        spain: "Spain",
        france: "France",
        austria: "Austria"
      }
    },
    email: {
      notification: {
        linkSummary: "Notification",
        success: {
          title: "You have successfully created a search subscription.",
          message:
            "Matching to your search you will now receive the latest offers by email."
        },
        email: {
          validation: "Please enter a valid email address.",
          address: "Your email address"
        },
        duplicated: {
          message:
            "Matching to your search you will now receive the latest offers by email.",
          title: "You have successfully subscribed this search."
        },
        dont: {
          miss: {
            bold: "Don't want to miss any interesting offers?",
            _value: "Then just subscribe to our search subscription."
          }
        },
        error: {
          message: "We apologize for the inconvenience.",
          title: "An error has occurred."
        },
        link: "New email notifications",
        dataPrivacyConsent: {
          nl:
            'Ik verklaar hierbij akkoord te gaan met de <a href="https://www.autoscout24.nl/bedrijf/privacy-verklaring/">Privacyverklaring</a> van AutoScout24.',
          de:
            'Ich stimme der <a href="https://www.autoscout24.de/unternehmen/datenschutzeinwilligung/">Datenschutzeinwilligung</a> von AutoScout24 zu.'
        },
        legalText: "NOTUSED",
        limit: {
          exceeded: {
            message: "You can save up to 50 searches!"
          }
        }
      }
    },
    sortKey: {
      select: {
        standard: "Sorted by: Standard",
        mileage: {
          descending: "Mileage descending",
          ascending: "Mileage ascending"
        },
        price: {
          descending: "Price descending",
          ascending: "Price ascending"
        },
        label: "Sort",
        new_standard: "Beste Ergebnisse",
        year: {
          ascending: "First registration ascending",
          descending: "First registration descending"
        },
        age: {
          descending: "Latest offers first"
        },
        finance: {
          rate: {
            ascending: "Rate ascending",
            descending: "Rate descending"
          }
        },
        make: {
          descending: "Make/Model descending",
          ascending: "Make/Model ascending"
        },
        power: {
          descending: "Power descending",
          ascending: "Power ascending"
        },
        distance: "By distance"
      }
    },
    page: {
      list: {
        legalnotice: {
          dealerprice: "Dealer price",
          envkv:
            'Weitere Informationen zum offiziellen Kraftstoffverbrauch und den offiziellen spezifischen CO2-Emissionen neuer Personenkraftwagen können dem "Leitfaden über den Kraftstoffverbrauch, die CO2-Emissionen und den Stromverbrauch neuer Personenkraftwagen" entnommen werden, der an allen Verkaufsstellen und bei der Deutschen Automobil Treuhand GmbH unter www.dat.de unentgeltlich erhältlich ist.',
          vat: "VAT deductible"
        },
        transporter: {
          title: "Used transporters for sale - AutoScout24",
          metaDescription:
            "Find new and used transporter offers on AutoScout24 - Europe's biggest online automotive marketplace."
        },
        empty: {
          result:
            "Sorry, there were no results found for your search. Please update your search criteria.",
          filter: {
            tags: {
              title:
                "Sometimes less is more: try it again with less or different filters. "
            }
          },
          header: "Oh snap!",
          title: "Unfortunately we couldn’t find any search results.",
          search: {
            subscriptions: {
              title:
                "This is exactly what you want/ looking for? Save this search - we’ll be in touch as soon as we get your perfect match."
            }
          }
        },
        car: {
          metaDescription: {
            makeModelFuelType:
              "Find new and used {0} {1} {2} offers on AutoScout24 - Europe's biggest online automotive marketplace.",
            _value:
              "Find new and used cars offers on AutoScout24 - Europe's biggest online automotive marketplace.",
            makeModelBodyColor:
              "Find {2} {0} {1} offers for sale on AutoScout24 - Europe's biggest online automotive marketplace.",
            makeModelFirstRegistration:
              "Find {0} {1} from {2} offers for sale on AutoScout24 - Europe's biggest online automotive marketplace.",
            makeModelCity:
              "Find new and used {0} {1} offers in {2} on AutoScout24 - Europe's biggest online automotive marketplace.",
            fuelType:
              "Here's where you'll find used {0} models at AutoScout24, Europe's biggest online automotive marketplace.",
            bodyType:
              "Here's where you'll find current {0} used car listings at AutoScout24, Europe's biggest online automotive marketplace.",
            makeModel:
              "Find new and used {0} {1} offers on AutoScout24 - Europe's biggest online automotive marketplace.",
            makeModelGearing:
              "Find {0} {1} {2} offers for sale on AutoScout24 - Europe's biggest online automotive marketplace.",
            makeFuelType:
              "Here's where you'll find used {0} and {1} car listings at AutoScout24, Europe's biggest online automotive marketplace.",
            makeModelVersion:
              "Find {0} {1} {2} offers for sale on AutoScout24 - Europe's biggest online automotive marketplace.",
            make:
              "Here's where you'll find current {0} used car listings at AutoScout24, Europe's biggest online automotive marketplace.",
            makeBodyType:
              "Here's where you'll find current {0} in {1} used car listings at AutoScout24, Europe's biggest online automotive marketplace.",
            makeModelOffer:
              "Find {0} {1} {2} offers for sale on AutoScout24 - Europe's biggest online automotive marketplace.",
            makeCity:
              "Find new and used {0} offers in {1} on AutoScout24 - Europe's biggest online automotive marketplace.",
            makeModelBodyType:
              "Find new and used {0} {1} {2} offers on AutoScout24 - Europe's biggest online automotive marketplace."
          },
          title: {
            makeModelFuelType: "Used {0} {1} {2} for sale - AutoScout24",
            _value: "Used cars for sale - AutoScout24",
            makeModelBodyColor: "Find {2} {0} {1} for sale - AutoScout24",
            makeModelFirstRegistration:
              "Find {0} {1} from {2} for sale - AutoScout24",
            makeModelCity: "Find used {0} {1} in {2} - AutoScout24",
            fuelType: "Used {0} models at AutoScout24",
            bodyType: "Used {0} listings at AutoScout24",
            makeModel: "Used {0} {1} for sale - AutoScout24",
            makeModelGearing: "Find {0} {1} {2} for sale - AutoScout24",
            makeFuelType: "Used {0} listings and {1} models at AutoScout24",
            makeModelVersion: "Find {0} {1} {2} for sale - AutoScout24",
            make: "Used {0} listings at AutoScout24",
            makeBodyType: "Used {0} and {1} listings at AutoScout24",
            makeModelOffer: "Find {0} {1} {2} for sale - AutoScout24",
            makeCity: "Find used {0} in {1} - AutoScout24",
            makeModelBodyType: "Used {0} {1} {2} for sale - AutoScout24"
          }
        },
        recommendation: {
          message: "Maybe you’re interested in some of these vehicles. "
        },
        global: {
          client: {
            error: "The client made a bad request: {0}",
            bad: {
              request: "This request could not be handled."
            }
          },
          server: {
            error: "This page is not available for the moment."
          }
        },
        error: {
          title: "Listings at AutoScout24"
        },
        trailer: {
          title: "Used trailers for sale - AutoScout24",
          metaDescription:
            "Find new and used trailer offers on AutoScout24 - Europe's biggest online automotive marketplace."
        },
        not: {
          found: {
            error: "This page cannot be found."
          }
        },
        caravan: {
          metaDescription:
            "Find new and used caravan & motorhome offers on AutoScout24 - Europe's biggest online automotive marketplace.",
          title: "Used caravans and motorhomes for sale - AutoScout24"
        },
        bike: {
          metaDescription: {
            _value:
              "Here you will find current motorcycle offers at AutoScout24, Europe's largest online car market.",
            fuelType:
              "Here's where you'll find used {0} motorcycles at AutoScout24, Europe's biggest online automotive marketplace.",
            bodyType:
              "Here's where you'll find current {0} motorcycle listings at AutoScout24, Europe's biggest online automotive marketplace.",
            makeModel:
              "Here are the latest {0} {1} motorcycle deals at AutoScout24, Europe's largest online car market.",
            makeFuelType:
              "Here's where you'll find used {0} and {1} motorcycles at AutoScout24, Europe's biggest online automotive marketplace.",
            make:
              "Here's where you'll find used {0} motorcycles at AutoScout24, Europe's biggest online automotive marketplace.",
            makeBodyType:
              "Here's where you'll find current {0} motorcycles in {1} at AutoScout24, Europe's biggest online automotive marketplace."
          },
          title: {
            _value: "Motorcycle offers at AutoScout24",
            fuelType: "Used {0} models at AutoScout24",
            bodyType: "Used {0} listings at AutoScout24",
            makeModel: "Buy {0} {1} used - AutoScout24",
            makeFuelType: "Used {0} listings and {1} models at AutoScout24",
            make: "Used {0} listings at AutoScout24",
            makeBodyType: "Used {0} in {1} listings at AutoScout24"
          }
        }
      },
      seo: {
        metaDescription:
          "Find used and new {0} {1} offers {2} at AutoScout24, Europe's biggest car marketplace.",
        title: "Used {0} {1} offers {2} - AutoScout24",
        city: "in {0}"
      },
      topLink: "To the top"
    },
    searchSubscription: {
      saveSearchButtonShort: "Save",
      anonymousSubscriptionShowFormButtonShort: "Notification",
      saveSearchButton: "Save search",
      anonymousSubscriptionShowFormButton: "New email notifications",
      saveSearch: {
        notification: {
          message: {
            "70": "You can save up to 50 searches!",
            "83":
              "Via menu 'My Account: <a href=\"{0}\">My Searches</a>' you may access all your saved searches.",
            "91":
              "If you have an account with us, you can easily sign up with your email address and password.",
            "92":
              "Via menu 'My Account: <a href=\"{0}\">My Searches</a>' you may access all your saved searches.",
            "93":
              "Matching to your search you will now receive the latest offers by email.",
            "94": "Your search subscription has been deleted.",
            "95": "We apologize for the inconvenience.",
            "96": "Please try again later.",
            "97": "Your search subscription has been deleted."
          },
          title: {
            "70": "",
            "83": "Your search has been saved successfully.",
            "91": "To save your search, sign in please.",
            "92": "Your search has been saved successfully.",
            "93": "You have successfully subscribed this search.",
            "94": "You have successfully unsubscribed from this search.",
            "95": "An error has occurred.",
            "96": "Your search subscription could not be deleted.",
            "97": "You have already successfully unsubscribed from this search."
          }
        }
      },
      anonymousSubscription: {
        doiSendOutConfirmationMessage:
          "Please check your inbox if your e-mail has not been confirmed yet",
        errorMessage: "We apologize for the inconvenience.",
        errorTitle: "An error has occurred.",
        successMessage:
          "Matching to your search you will now receive the latest offers by email.",
        limitExceededErrorMessage: "You can save up to 50 searches!",
        duplicatedMessage:
          "Matching to your search you will now receive the latest offers by email.",
        successTitle: "You have successfully created a search subscription.",
        doiSendOutConfirmationTitle: "E-Mail address confirmed?"
      },
      anonymousSubscriptionForm: {
        subtitle: "Then just subscribe to our search subscription.",
        emailAddress: "Your email address",
        subscribeButton: "Subscribe",
        title: "Don't want to miss any interesting offers?",
        dataPrivacyConsentCheckbox:
          'Ich stimme der Verarbeitung meiner Daten, wie in der <a target="_blank" href="https://www.autoscout24.de/unternehmen/datenschutzeinwilligung/">Einwilligungserklärung</a> von AutoScout24 beschrieben, zu. Ich kann diese Einwilligung jederzeit mit Wirkung für die Zukunft widerrufen.'
      }
    },
    interior: {
      upholstery: {
        velour: "Velour",
        cloth: "Cloth",
        fullLeather: "Full Leather",
        partialLeather: "Partial Leather",
        material: {
          label: "Material"
        },
        other: "Other",
        alcantara: "Alcantara"
      }
    },
    saleMaskFragment: {
      ctaMarketplace: "In den nächsten Monaten",
      filters: {
        firstRegistration: "First Registration"
      },
      ctaButton: "Inserat erstellen",
      ctaCopy: "Wann planen Sie Ihren Autoverkauf?",
      ctaDirectsale: "So schnell wie möglich"
    },
    refinefilters: {
      button: {
        close: "Close"
      }
    },
    color: {
      gold: "Gold",
      violet: "Violet",
      blue: "Blue",
      beige: "Beige",
      grey: "Grey",
      silver: "Silver",
      orange: "Orange",
      green: "Green",
      black: "Black",
      bronze: "Bronze",
      yellow: "Yellow",
      brown: "Brown",
      white: "White",
      other: "Other",
      red: "Red"
    },
    others: "Others",
    filter: {
      summary: {
        heading: {
          title: {
            makeModelFuelType: "for {0} {1}",
            makeModelEngineType: "for {0} {1}",
            makeModelBodyColor: "for {1} in {0}",
            makeModelCity: "for {0} in {1}",
            makeModelRegistrationYear: "for {0} from {1}",
            makeModel: "for {0}",
            defaultVehicleIndicator: "for your search",
            oneSidedContract: "verfügbar für den Online-Kauf",
            makeModelOfferType: "for {0} {1}",
            vehicleIndicator: "for {0}",
            transactable: "online verfügbare Fahrzeuge",
            makeModelTransmission: "for {0} {1}",
            makeModelBodyType: "for {0} {1}",
            numberOfFilters: "+ {0} filters"
          },
          _value: "My search"
        },
        show: {
          accident: {
            also: "Show cars with accidents",
            only: "Only show cars with accidents"
          }
        },
        empty: "No filters selected",
        less: "Show fewer",
        dealer: {
          page: "Dealer stock"
        },
        vehicle: {
          emissionClass: "Euro Emission Class: Euro",
          emissionLabel: "Euro Emission Label:"
        },
        doors: "doors",
        dont: {
          show: {
            accident: "Don't show cars with accidents"
          }
        },
        seals: {
          generic: {
            other: "Other certified pre-owned cars",
            _value: "Certified Pre-Owned Cars"
          }
        },
        crossborder: "Cross-border",
        more: "Show more",
        seats: "seats",
        beds: "beds"
      },
      smartLabel: {
        topModels: "Top models",
        topMakes: "Top makes"
      }
    },
    select: {
      all: "All",
      label: "Please choose"
    },
    around: "{0} km around {1}",
    button: {
      search: {
        bikes: "bikes",
        vehicle: "vehicle",
        cars: "cars",
        subscribe: "Subscribe",
        vehicles: "vehicles",
        car: "car",
        bike: "bike"
      },
      refineSummary: "Filter",
      refine: "Refine search",
      save: {
        search: {
          subscription: "Save search",
          subscriptionSummary: "Save"
        }
      }
    },
    general: {
      notification: {
        client: {
          message:
            "Unfortunately, your search could not be conducted correctly. Please check your connection and try again."
        },
        server: {
          message:
            "Sorry, something went wrong. Please refresh this page or try again later."
        },
        noResults: {
          message:
            "Note: Unfortunately, the last set filter will not deliver any results."
        }
      }
    },
    newfilters: {
      button: {
        refine: "Alternative filter view"
      },
      mostUsed: {
        details: "Basic specifications & Location"
      },
      offerDetails: {
        details: "Offer details"
      },
      heading: "Filter by"
    },
    otFreeRegCampaign: {
      description:
        "Nur für kurze Zeit: Jetzt mit kostenloser Lieferung und Fahrzeuganmeldung.",
      title: "Aktionswochen beim Online-Kauf!"
    },
    breadcrumb: {
      back: "Back"
    },
    filters: {
      locationAndSeller: {
        details: "Location, Seller"
      },
      location: {
        areaSearch: {
          example: {
            fr: "e.g. Paris or 75001",
            it: "e.g. Roma or 00184",
            nl: "e.g. Amsterdam or 1012 CZ",
            de: "e.g. Berlin or 10243",
            at: "e.g. Wien or 1010",
            default: "City/ZIP",
            es: "e.g. Madrid or 28013",
            be: "e.g. Bruxelles or 1000",
            lu: "e.g. Luxembourg or L-2314",
            com: "Select a country"
          },
          error: "Unknown city or zip code",
          _value: "City/zip code"
        },
        countries: {
          tooltip:
            "Find vehicles near you. Please enter place or ZIP code in the language of the vehicle location.",
          _value: "Countries"
        },
        radius: "Radius (km)",
        crossborder: "Cross-border"
      },
      model: {
        autocomplete: {
          empty: "Unknown model",
          placeholder: "All"
        }
      },
      ot: {
        info_button: {
          section2:
            'Dazu einfach den Filter "Online kaufen" setzen, Ihr Wunschauto auswählen und "zum Online-Kauf" klicken.',
          heading: "AutoScout24 OneClick. Wunschauto online kaufen.",
          section1:
            "Sie möchten nicht lange auf Ihr Traumauto warten? Dann greifen Sie gleich zu. Bestellen Sie Ihr neues Auto ganz einfach online & verbindlich. Rund um die Uhr. Und mit 14 Tagen Widerrufsrecht!",
          section3: "So einfach wie Klamotten shoppen!"
        },
        filter_tag: "Online kaufen",
        label: "Online kaufen"
      },
      vehicle: {
        envSticker: {
          tooltip: "Environmental stamp for circulation in Germany"
        },
        seals: {
          otherSeals: {
            description: "Cars with technical check and guarantee",
            label: "Other Certified Pre-Owned Cars"
          },
          genericSeal: {
            description: "Cars with technical check and guarantee",
            _value: "Certified Pre-Owned Cars"
          }
        },
        car: {
          details: "Basic specifications"
        },
        previousOwners: "Previous Owners",
        condition: "Vehicle condition",
        nonSmokingVehicle: "Non-smoking Vehicle",
        fullService: "Full Service",
        environmentalSticker: "Emission Label (at least)",
        environment: "Environment",
        details: {
          numberOfAxles: "Axle count",
          vat: "VAT deductible",
          _value: "Basic specifications",
          mileage: "Mileage",
          price: "Price",
          fuel: {
            type: "Fuel type"
          },
          newDriverSuitable: "For new drivers",
          gear: "Gear",
          finance: {
            rate: "Finance Rate"
          },
          priceEvaluation: {
            topPrice: "TOP PRICE",
            tryAgainSoon: "Try to reload the page in some minutes again.",
            expensivePrice: "EXPENSIVE PRICE",
            greyLabel: "No Information",
            somewhatExpensivePrice: "SOMEWHAT EXPENSIVE PRICE",
            link: "What is this?",
            title: "AutoScout24 Price Estimation",
            goodPrice: "GOOD PRICE",
            noPriceInformationAvailable: "No price information available.",
            fairPrice: "FAIR PRICE"
          },
          first: {
            registration: "First registration"
          },
          power: "Power",
          numberOfBeds: "Nr. of Beds"
        },
        emissionClass: "Euro Emission Class (at least)",
        hadAccident: "Had Accident",
        type: {
          body: {
            type: "Body type"
          },
          _value: "Body type",
          doors: "Nr. of Doors",
          seats: "Nr. of Seats",
          bike: {
            type: "Type"
          }
        },
        generalInspectionEmission: {
          _value: "General Inspection",
          tooltip: "Vehicles with general inspection and emission check."
        },
        bike: {
          details: "Basic specifications"
        },
        particulateFilter: "Particulate filter"
      },
      interior: {
        color: "Interior Color",
        details: "Interior",
        upholstery: "Upholstery"
      },
      makeModel: {
        extended: {
          versionExample: "Enter model variant"
        },
        model: "Model",
        car: {
          versionExample: "e.g. Plus, GTI, etc."
        },
        edit: "Edit",
        version: "Variant",
        details: "Make, Model, Version",
        make: "Make",
        bike: {
          versionExample: "e.g. special, super, etc.",
          details: "Make, Model, Type"
        },
        add: "Add more Makes/Models"
      },
      range: {
        to: "to",
        from: "from",
        starting: {
          from: "from"
        },
        upto: "up to"
      },
      priceEvaluation: {
        categories: {
          fair: {
            price: {
              text: "Fair Price"
            }
          },
          somewhatExpensive: {
            price: {
              text: "Somewhat Expensive"
            }
          },
          good: {
            price: {
              text: "Good Price"
            }
          },
          expensive: {
            price: {
              text: "Expensive"
            }
          },
          na: {
            price: {
              text: "No Information"
            }
          },
          top: {
            price: {
              text: "Top Price"
            }
          }
        }
      },
      make: {
        autocomplete: {
          empty: "Unknown make",
          all: "All makes",
          others: "Other makes",
          placeholder: "All",
          top: "Top makes"
        }
      },
      reset: "Remove all filters",
      firstreg: {
        autocomplete: {
          empty: "Invalid range"
        }
      },
      seller: {
        onlineSince: "Online since",
        warranty: "Warranty",
        customerType: "Seller"
      },
      equipments: {
        comfort: "Comfort & Convenience",
        sport: "Sports",
        entertainment: "Entertainment & Media",
        details: "Equipment",
        accessories: "Accessories",
        safety: "Safety & Security"
      },
      exterior: {
        rims: "Rims",
        body: {
          color: "Body Color"
        },
        paintwork: "Paintwork",
        details: "Exterior"
      },
      search: {
        criteria: "Search Criteria"
      }
    },
    ai: {
      search: {
        switch: "Zur Standardsuche",
        placeholder: {
          "0": "Passat Jahreswagen mit Anhängerkupplung",
          "1": "VW Polo zwischen 2010 und 2015",
          "2": "Kleinwagen unter 10.000€ ab 2015",
          "3": "Tiguan mit Standheizung in Hamburg",
          "4": "Ford Focus mit Schiebedach bis 20000 Euro",
          "5": "Audi A4 geschaltet mit Allrad, Xenon und Navi",
          "6": "Porsche 911 Turbo in München"
        },
        home: {
          new: "Neu",
          back: "Smarte Textsuche"
        },
        moto: {
          mobile: {
            placeholder: {
              "0": "Piaggio Zip 50 unter 5000 km",
              "1": "BMW C1 mit Katalysator",
              "2": "Supersport in silber oder schwarz",
              "3": "KTM blau 6000€",
              "4": "Adventure München mit Garantie",
              "5": "Harley-Davidson ab 2016 Berlin",
              "6": "Roter Tourer 1960-1980"
            }
          },
          placeholder: {
            "0": "BMW Adventure neu mit Katalysator",
            "1": "Piaggio Zip 50 zwischen 2010 und 2015",
            "2": "Roller unter 2.000€ ab 2015",
            "3": "Triumph mit Katalysator in Hamburg",
            "4": "Honda mit Katalysator bis 3500 Euro",
            "5": "KTM blau 6000€",
            "6": "Harley-Davidson ab 2016 Berlin"
          }
        },
        mobile: {
          placeholder: {
            "0": "VW Golf unter 5000 km",
            "1": "Ford Focus neu mit Schiebedach",
            "2": "Limousine in silber oder schwarz",
            "3": "Opel Corsa rot 6000€",
            "4": "A4 München mit Garantie",
            "5": "Tesla ab 2016 Berlin",
            "6": "Rotes Coupe 1960-1980"
          }
        },
        input: {
          suggestions: "Suggestions",
          placeholder: "Geben Sie Ihre Wunschkriterien ein",
          defaultSuggestions: {
            "0": "Station wagon with 4WD",
            "1": "Audi in Berlin",
            "2": "Van less than 10.000 km",
            "3": "SUV below 10.000 Euro",
            "4": "V8 in red or black 1970-1980"
          },
          button: "Search",
          lastSearches: "Recent searches"
        }
      }
    },
    vehicleDetails: {
      usageState: {
        NewUsedAccident: "Show also",
        Accident: "Show only",
        NewUsed: "Don't show"
      },
      fuelType: {
        electric: "Electric",
        gasoline: "Gasoline",
        _value: "Fuel",
        twostroke: "Two Stroke Gasoline",
        electricGasoline: "Electric/Gasoline",
        hydrogene: "Hydrogene",
        others: "Others",
        gas: "Gas",
        diesel: "Diesel",
        ethanol: "Ethanol",
        lpg: "Liquified petroleum gas (LPG)",
        cng: "Compressed natural gas (CNG)",
        electricDiesel: "Electric/Diesel"
      },
      bodyType: {
        sedan: "Sedans",
        transporter: "Transporter",
        _value: "Body",
        coupe: "Coupe",
        van: "Van",
        convertible: "Convertible",
        bike: {
          chopper: "Chopper",
          touringenduro: "Touring Enduro",
          tourer: "Tourer",
          supermoto: "Supermoto",
          moped: "Moped",
          trial: "Trials Bike",
          classic: "Classic",
          scooter: "Scooter",
          trike: "Three Wheeler",
          supersport: "Supersport",
          others: "Others",
          streetfighter: "Streetfighter",
          quad: "Quad",
          naked: "Naked Bike",
          minibike: "Minibike",
          cross: "Motocross",
          rally: "Rally",
          racing: "Racing",
          enduro: "Enduro Bike",
          sidecar: "Sidecar",
          sporttouring: "Sport Touring"
        },
        offroad: "SUV/Off-Road",
        other: "Other",
        stationwagon: "Station wagon",
        compact: "Compact"
      },
      gearingType: {
        automatic: "Automatic",
        semiautomatic: "Semi-automatic",
        manual: "Manual"
      },
      articleOfferType: {
        Demonstration: "Demonstration",
        Used: "Used",
        AntiqueClassic: "Antique / Classic",
        Preregistered: "Pre-registered",
        New: "New",
        EmployeeCar: "Employee's Car"
      },
      power: {
        hp: "hp",
        kw: "kW"
      },
      sentence: {
        articleOfferType: {
          Demonstration: "",
          Used: "",
          AntiqueClassic: "",
          Preregistered: "",
          New: "",
          EmployeeCar: ""
        },
        gearingType: {
          manual: "",
          semiautomatic: "",
          automatic: ""
        }
      }
    },
    header: {
      sticky: {
        results: "Search results",
        result: "Search result"
      },
      results: "Offers",
      result: "Offers"
    },
    equipment: {
      car: {
        hideRemainingFilters: "Show less equipment",
        showRemainingFilters: "Show more equipment"
      },
      bedtype: {
        title: "Bed type"
      }
    },
    detail: {
      search: "Detail Search"
    },
    personalization: {
      overlay: {
        text:
          "Durch die Angabe Ihrer persönlichen Daten können wir Ihnen in Zukunft individualisierte Finanzierungs- und Versicherungsraten anzeigen. Dieses Feature ist noch in Bearbeitung.",
        button: {
          yes: "Ja, würde ich nutzen!",
          no: "Nein danke!"
        },
        feedback:
          "Bitte geben Sie uns Feedback, ob dieses Feature für Sie interessant wäre:",
        headline: "Bald verfügbar!"
      },
      button: {
        highlight: "Neu!",
        text: "Finanzierungsrate personalisieren"
      }
    },
    pagination: {
      next: "Next",
      previous: "Previous"
    },
    seller: {
      customerType: {
        private: "Private",
        dealer: "Dealer"
      },
      onlineSince: {
        weeks: "weeks",
        day: "day",
        days: "days",
        week: "week"
      }
    },
    and: "and",
    exterior: {
      paintwork: {
        metallic: "Metallic"
      },
      rims: {
        alloyWheels: "Alloy Wheels"
      }
    },
    searchMaskFragment: {
      filters: {
        location: {
          radius: "Radius"
        },
        model: "Model",
        price: {
          to: "Price to (€)"
        },
        crossborder: "Cross-border",
        financeRate: {
          to: "Rate to (€)"
        },
        firstRegistration: {
          from: "First Registration"
        },
        bodytype: "Body type",
        make: "Make",
        offer: {
          new: "New",
          used: "Used",
          preregistered: "Preregistered"
        }
      },
      error: {
        header: "Sorry. That should not have happened.",
        button: "Go to our list page",
        copyright: "© Copyright 2017 AutoScout24 GmbH. All Rights reserved."
      },
      refineSearch: "Refine Search",
      count: {
        results: "{0} results",
        notAvailable: "Show Results",
        resultTmpl: "$count results",
        resultsTmpl: "$count results"
      }
    }
  },
  logging: {
    provider: "remote"
  },
  showAiSearchByDefault: false,
  defaultUsageState: ["N", "U"],
  allNumberOfAxles: [],
  allFuelTypeIds: [
    {
      id: "B",
      name: "Gasoline"
    },
    {
      id: "D",
      name: "Diesel"
    },
    {
      id: "M",
      name: "Ethanol"
    },
    {
      id: "E",
      name: "Electric"
    },
    {
      id: "H",
      name: "Hydrogen"
    },
    {
      id: "L",
      name: "LPG"
    },
    {
      id: "C",
      name: "CNG"
    },
    {
      id: "2",
      name: "Electric/Gasoline"
    },
    {
      id: "O",
      name: "Others"
    },
    {
      id: "3",
      name: "Electric/Diesel"
    }
  ],
  allGearIds: [
    {
      id: "M",
      name: "Manual"
    },
    {
      id: "A",
      name: "Automatic"
    },
    {
      id: "S",
      name: "Semi-automatic"
    }
  ],
  allAccidentOptions: {
    newUsed: ["N", "U"],
    accidentNewUsed: ["A", "N", "U"],
    accident: ["A"]
  },
  allOnlineSinceIds: [
    {
      id: "1",
      name: "1 day"
    },
    {
      id: "2",
      name: "2 days"
    },
    {
      id: "3",
      name: "3 days"
    },
    {
      id: "4",
      name: "4 days"
    },
    {
      id: "5",
      name: "5 days"
    },
    {
      id: "6",
      name: "6 days"
    },
    {
      id: "7",
      name: "1 week"
    },
    {
      id: "14",
      name: "2 weeks"
    }
  ],
  allUpholsteryIds: [
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
  ],
  allEmissionClassValues: [
    {
      id: 1,
      name: "Euro 1"
    },
    {
      id: 2,
      name: "Euro 2"
    },
    {
      id: 3,
      name: "Euro 3"
    },
    {
      id: 4,
      name: "Euro 4"
    },
    {
      id: 5,
      name: "Euro 5"
    },
    {
      id: 6,
      name: "Euro 6"
    },
    {
      id: 7,
      name: "Euro 6c"
    },
    {
      id: 9,
      name: "Euro 6d-TEMP"
    },
    {
      id: 8,
      name: "Euro 6d"
    }
  ],
  allEmissionStickerValues: [
    {
      id: 2,
      name: "min. 2 (Red)"
    },
    {
      id: 3,
      name: "min. 3 (Yellow)"
    },
    {
      id: 4,
      name: "min. 4 (Green)"
    }
  ],
  grossWeight: {
    range: [],
    label: "Perm. GVW"
  },
  firstRegistrationRange: {
    from: 1900,
    to: 2020
  },
  financeRateSteps: [
    {
      id: 50,
      name: "€50"
    },
    {
      id: 100,
      name: "€100"
    },
    {
      id: 200,
      name: "€200"
    },
    {
      id: 300,
      name: "€300"
    },
    {
      id: 400,
      name: "€400"
    },
    {
      id: 500,
      name: "€500"
    },
    {
      id: 600,
      name: "€600"
    },
    {
      id: 700,
      name: "€700"
    }
  ],
  seatsRange: {
    from: 1,
    to: 12
  },
  bedsRange: {
    from: 1,
    to: 7
  },
  countries: [
    {
      key: "eu",
      value: "",
      label: "Europe"
    },
    {
      key: "at",
      value: "A",
      label: "Austria"
    },
    {
      key: "be",
      value: "B",
      label: "Belgium"
    },
    {
      key: "de",
      value: "D",
      label: "Germany"
    },
    {
      key: "es",
      value: "E",
      label: "Spain"
    },
    {
      key: "fr",
      value: "F",
      label: "France"
    },
    {
      key: "it",
      value: "I",
      label: "Italy"
    },
    {
      key: "lu",
      value: "L",
      label: "Luxemburg"
    },
    {
      key: "nl",
      value: "NL",
      label: "Netherlands"
    }
  ],
  zipRadiusStepsConfig: {
    defaultRadius: 200,
    steps: [
      {
        value: 10,
        display: "10 km"
      },
      {
        value: 20,
        display: "20 km"
      },
      {
        value: 50,
        display: "50 km"
      },
      {
        value: 100,
        display: "100 km"
      },
      {
        value: 150,
        display: "150 km"
      },
      {
        value: 200,
        display: "200 km"
      },
      {
        value: 250,
        display: "250 km"
      },
      {
        value: 300,
        display: "300 km"
      },
      {
        value: 400,
        display: "400 km"
      }
    ]
  },
  countryForTld: "",
  isLanguageVersionCountry: true,
  initialTotalCount: 2286125,
  endpoints: {
    models: {
      url: "https://search-filters-provider.a.autoscout24.com/graphql",
      user: "revolistion",
      pass: "23695c1bd68c543e26f58a00d62e97ce"
    },
    totalCount: {
      url:
        "https://classified-search.a.autoscout24.com/classified-search/totalcount",
      user: "classified-list-js",
      pass: "edf3f3f9309ee126f8ae7e0344ae592b"
    },
    geoSuggestions: {
      url:
        "https://geocode.a.autoscout24.com/autocomplete/{:language}/{:search}",
      user: "classified-list-js",
      pass: "884656f65765dc240f30caf8393974a6"
    },
    geoResolution: {
      url: "https://geocode.a.autoscout24.com/resolution/{:language}/{:id}",
      user: "classified-list-js",
      pass: "884656f65765dc240f30caf8393974a6"
    },
    normalSearch: {
      url: "/results",
      user: null,
      pass: null
    },
    refineSearch: {
      url: "/refinesearch",
      user: null,
      pass: null
    },
    listings: {
      url:
        "/classified-list/react-listelements/en?&toguru=ignite-v2-detailpage-redirect%3Dfalse%7Cgecloud-ops-financing-example-fragment%3Dfalse%7Cs24-media-ab-test-3%3Dfalse%7Conline-transaction-info%3Dfalse%7Conline-transaction-nl-price-test-ab%3Dfalse%7Ccxp-retention-watchlist-sort%3Dfalse%7Ccxp-eng-ops-reco-dealer-push-b%3Dfalse%7Cgecloud-recommender-shadow%3Dfalse%7Cfed-sbr-nl-test%3Dfalse%7Ccldt-make-model-category-links%3Dtrue%7Ccxp-rebranding%3Dfalse%7Cgecloud-ops-financing-example%3Dfalse%7Cgecloud-ops-optimizely%3Dtrue%7Cs24-media-osa-rollout%3Dtrue%7Ctransaction-fragment-render%3Dfalse%7Cbrand-variation-google-blue%3Dfalse%7Ccxp-dekra-watchlist-panel%3Dfalse%7Cdealer-info-homepage%3Dtrue%7Ccxp-tuev-watchlist-panel%3Dfalse%7Conline-transaction-buynow-button-checkout%3Dfalse%7Ccxp-sell-service-abracar%3Dtrue%7Ckondor-refresh-cache%3Dtrue%7Cfed-tuv-reports-section%3Dfalse%7Cbrand-variation-coral%3Dfalse%7Conline-transaction-buynow-button%3Dfalse%7Ccxp-retention-fuse-lightbox-bell%3Dfalse%7Ccxp-eng-ops-seo-index%3Dtrue%7Cfed-car-check-section%3Dfalse%7Ckondor-ops-price-authority-consumer-web%3Dfalse%7Cdmm-online-transaction-listing-label%3Dfalse%7Cgecloud-ops-lazyload-summary-images%3Dtrue%7Cfed-netherlands-sbr-and-new-stage-links%3Dfalse%7Cfed-show-slice-on-top%3Dfalse%7Cfed-car-check-trapdoor%3Dfalse%7Cs24-media-ab-test-1%3Dfalse%7Cgecloud-ops-fed-params-for-raw-mail-templates%3Dtrue%7Cbrand-variation-jeans-blue%3Dfalse%7Ccxp-retention-ops-fuse-signup%3Dfalse%7Ccxp-recaptcha-v3%3Dtrue%7Cbrand-variation-yellow%3Dfalse%7Ckondor-test%3Dfalse%7Cdealer-info-migration%3Dfalse%7Cs24-media-ab-test-2%3Dfalse%7Cgecloud-ops-event-publisher%3Dtrue%7Cs24-media-osa-rollout-ab-test%3Dfalse%7Ccxp-new-list-item%3Dtrue%7Cfed-ops-italy-agos-stage%3Dfalse%7Cs24-media-billboard-listpage-test%3Dfalse%7Cfed-ops-belgium-axa%3Dfalse%7Cfed-ops-germany-leasing%3Dfalse%7Cgecloud-ops-list-rate-overlay%3Dfalse%7Cgecloud-lead-form-experiment%3Dtrue%7Cfed-sbr-nl-show%3Dfalse%7Cgecloud-ops-fed-afterlead-integration%3Dtrue%7Cgecloud-ignite-no-parkdeck-on-lead%3Dfalse%7Cgecloud-ops-gallery-edf-ad%3Dfalse%7Conline-transaction-nl-price-test%3Dfalse%7Ccxp-eng-web-recaptcha-v3-test%3Dfalse%7Cgecloud-classified-detail-without-media%3Dtrue%7Ccxp-eng-ops-ads%3Dtrue%7Cgecloud-ops-seals%3Dtrue%7Cfed-ops-italy-agos-slice%3Dfalse&isSeoListPage=false",
      user: null,
      pass: null
    },
    searchSubscriptionAnonymous: {
      url: "/search-subscriptions/api/subscriptions/subscribe",
      user: null,
      pass: null
    },
    searchSubscriptionSaveSearch: {
      url: "/search-subscriptions/api/save-subscription",
      user: null,
      pass: null
    },
    searchSubscriptionLastSearch: {
      url: "/search-subscriptions/api/last-search",
      user: null,
      pass: null
    },
    priceEvaluationExplanation: {
      url:
        "/priceevaluation/price-label-explanation-fragment?culture=en-GB&toguru=kondor-refresh-cache=true|kondor-ops-price-authority-consumer-web=false|kondor-expensive-price-category=false|kondor-test=false|kondor-explanation-v2=false&PageSpeed=off&ModPagespeed=off",
      user: null,
      pass: null
    },
    aiSearch: {
      url:
        "/classified-list/ai-search/search/%7B:searchText%7D?articleType=%7B%3AarticleType%7D&countryCode=com",
      user: null,
      pass: null
    },
    aiSearchConfig: {
      url: "/classified-list/react-filters/ai-search/config/?culture=en-GB",
      user: null,
      pass: null
    },
    aiSearchSuggestion: {
      url:
        "/classified-list/ai-search/autocomplete/%7B:searchTerm%7D?articleType=%7B%3AarticleType%7D",
      user: null,
      pass: null
    }
  },
  listPageUrl: "/results",
  refineSearchUrl: "/refinesearch",
  savedSearchesUrl: "/account/searches",
  localStorage: {
    key: "as24HomeFilters",
    version: 63
  },
  mileageSteps: [
    {
      id: 2500,
      name: "2,500"
    },
    {
      id: 5000,
      name: "5,000"
    },
    {
      id: 10000,
      name: "10,000"
    },
    {
      id: 20000,
      name: "20,000"
    },
    {
      id: 30000,
      name: "30,000"
    },
    {
      id: 40000,
      name: "40,000"
    },
    {
      id: 50000,
      name: "50,000"
    },
    {
      id: 60000,
      name: "60,000"
    },
    {
      id: 70000,
      name: "70,000"
    },
    {
      id: 80000,
      name: "80,000"
    },
    {
      id: 90000,
      name: "90,000"
    },
    {
      id: 100000,
      name: "100,000"
    },
    {
      id: 125000,
      name: "125,000"
    },
    {
      id: 150000,
      name: "150,000"
    },
    {
      id: 175000,
      name: "175,000"
    },
    {
      id: 200000,
      name: "200,000"
    }
  ],
  tld: "COM",
  isAggregatedOfferTypes: true,
  toggles: {
    crossBorderList: true,
    priceEvaluationFilters: false,
    financeRateFilter: false,
    stickyListPageButtonExperiment: true,
    financeRateFilterHome: false,
    opsToggleHideSubscribeFragment: false,
    aiSearchbox: false,
    aiSearchAutocomplete: true,
    oscFilter: true,
    oscFilterInfoButton: false,
    showSubscriptionFunnelTab: false,
    smartMakeModelFilters: false,
    consumptionFilter: true,
    onlineTransactionCustomListpage: false,
    listpageBottomLinks: true,
    hideSubscriptionWhenOneClick: false,
    isRebrandingVariation: false,
    showSubscribeFragment: true,
    deleteLastSearches: true,
    emptyAutocompleteSuggestions: false
  },
  showOscFilter: false,
  allPriceEvaluationCategories: [],
  allOfferTypes: [
    {
      key: {
        id: "N",
        name: "New"
      },
      values: ["N"]
    },
    {
      key: {
        id: "U",
        name: "Used"
      },
      values: ["U", "D", "J", "O"]
    },
    {
      key: {
        id: "S",
        name: "Pre-registered"
      },
      values: ["S"]
    }
  ],
  equipments: [
    {
      id: "1",
      name: "ABS"
    },
    {
      id: "2",
      name: "Driver-side airbag"
    },
    {
      id: "3",
      name: "Passenger-side airbag"
    },
    {
      id: "4",
      name: "Sunroof"
    },
    {
      id: "10",
      name: "Radio"
    },
    {
      id: "11",
      name: "4WD"
    },
    {
      id: "13",
      name: "Power windows"
    },
    {
      id: "15",
      name: "Alloy wheels"
    },
    {
      id: "17",
      name: "Central door lock"
    },
    {
      id: "18",
      name: "Alarm system"
    },
    {
      id: "23",
      name: "Navigation system"
    },
    {
      id: "26",
      name: "Immobilizer"
    },
    {
      id: "32",
      name: "Side airbag"
    },
    {
      id: "34",
      name: "Seat heating"
    },
    {
      id: "36",
      name: "Handicapped enabled"
    },
    {
      id: "38",
      name: "Cruise control"
    },
    {
      id: "39",
      name: "Xenon headlights"
    },
    {
      id: "41",
      name: "On-board computer"
    },
    {
      id: "42",
      name: "Electronic stability control"
    },
    {
      id: "19",
      name: "Fog lights"
    },
    {
      id: "20",
      name: "Trailer hitch"
    },
    {
      id: "5",
      name: "Air conditioning"
    },
    {
      id: "27",
      name: "Roof rack"
    },
    {
      id: "12",
      name: "Power steering"
    },
    {
      id: "30",
      name: "Automatic climate control"
    },
    {
      id: "31",
      name: "Traction control"
    },
    {
      id: "16",
      name: "Electrically adjustable seats"
    },
    {
      id: "43",
      name: "MP3"
    },
    {
      id: "50",
      name: "Panorama roof"
    },
    {
      id: "52",
      name: "Auxiliary heating"
    },
    {
      id: "112",
      name: "Sport package"
    },
    {
      id: "113",
      name: "Start-stop system"
    },
    {
      id: "114",
      name: "Multi-function steering wheel"
    },
    {
      id: "115",
      name: "Daytime running lights"
    },
    {
      id: "116",
      name: "Sport suspension"
    },
    {
      id: "117",
      name: "Sport seats"
    },
    {
      id: "118",
      name: "Adaptive headlights"
    },
    {
      id: "119",
      name: "Ski bag"
    },
    {
      id: "133",
      name: "Adaptive Cruise Control"
    },
    {
      id: "134",
      name: "Armrest"
    },
    {
      id: "135",
      name: "Electrically heated windshield"
    },
    {
      id: "136",
      name: "Heated steering wheel"
    },
    {
      id: "137",
      name: "Hill Holder"
    },
    {
      id: "138",
      name: "Digital radio"
    },
    {
      id: "139",
      name: "Electric tailgate"
    },
    {
      id: "140",
      name: "LED Headlights"
    },
    {
      id: "141",
      name: "LED Daytime Running Lights"
    },
    {
      id: "142",
      name: "Leather steering wheel"
    },
    {
      id: "143",
      name: "Lumbar support"
    },
    {
      id: "144",
      name: "Air suspension"
    },
    {
      id: "145",
      name: "Massage seats"
    },
    {
      id: "146",
      name: "Driver drowsiness detection"
    },
    {
      id: "147",
      name: "Night view assist"
    },
    {
      id: "148",
      name: "Emergency brake assistant"
    },
    {
      id: "149",
      name: "Emergency system"
    },
    {
      id: "150",
      name: "Tire pressure monitoring system"
    },
    {
      id: "151",
      name: "Shift paddles"
    },
    {
      id: "152",
      name: "Sliding door"
    },
    {
      id: "153",
      name: "Keyless central door lock"
    },
    {
      id: "154",
      name: "Seat ventilation"
    },
    {
      id: "155",
      name: "Sound system"
    },
    {
      id: "156",
      name: "Voice Control"
    },
    {
      id: "157",
      name: "Lane departure warning system"
    },
    {
      id: "158",
      name: "Blind spot monitor"
    },
    {
      id: "159",
      name: "Touch screen"
    },
    {
      id: "160",
      name: "Television"
    },
    {
      id: "161",
      name: "USB"
    },
    {
      id: "162",
      name: "Traffic sign recognition"
    },
    {
      id: "121",
      name: "Electrical side mirrors"
    },
    {
      id: "122",
      name: "Bluetooth"
    },
    {
      id: "123",
      name: "Heads-up display"
    },
    {
      id: "124",
      name: "Hands-free equipment"
    },
    {
      id: "125",
      name: "Isofix"
    },
    {
      id: "126",
      name: "Light sensor"
    },
    {
      id: "127",
      name: "Rain sensor"
    },
    {
      id: "128",
      name: "Parking assist system sensors front"
    },
    {
      id: "129",
      name: "Parking assist system sensors rear"
    },
    {
      id: "130",
      name: "Parking assist system camera"
    },
    {
      id: "131",
      name: "Parking assist system self-steering"
    },
    {
      id: "132",
      name: "CD player"
    }
  ],
  conditionEquipments: [
    {
      id: "37",
      name: "Guarantee"
    },
    {
      id: "49",
      name: "With full service history"
    },
    {
      id: "53",
      name: "Particulate filter"
    },
    {
      id: "110",
      name: "Non-smoking vehicle"
    }
  ],
  allBodyColorIds: [
    {
      id: 1,
      name: "Beige",
      cssClassName: "beige"
    },
    {
      id: 2,
      name: "Blue",
      cssClassName: "blue"
    },
    {
      id: 3,
      name: "Brown",
      cssClassName: "brown"
    },
    {
      id: 4,
      name: "Bronze",
      cssClassName: "bronze"
    },
    {
      id: 5,
      name: "Yellow",
      cssClassName: "yellow"
    },
    {
      id: 6,
      name: "Grey",
      cssClassName: "grey"
    },
    {
      id: 7,
      name: "Green",
      cssClassName: "green"
    },
    {
      id: 10,
      name: "Red",
      cssClassName: "red"
    },
    {
      id: 11,
      name: "Black",
      cssClassName: "black"
    },
    {
      id: 12,
      name: "Silver",
      cssClassName: "silver"
    },
    {
      id: 13,
      name: "Violet",
      cssClassName: "violet"
    },
    {
      id: 14,
      name: "White",
      cssClassName: "white"
    },
    {
      id: 15,
      name: "Orange",
      cssClassName: "orange"
    },
    {
      id: 16,
      name: "Gold",
      cssClassName: "gold"
    }
  ],
  allInteriorColorIds: [
    {
      id: 1,
      name: "Beige",
      cssClassName: "beige"
    },
    {
      id: 2,
      name: "Black",
      cssClassName: "black"
    },
    {
      id: 3,
      name: "Grey",
      cssClassName: "grey"
    },
    {
      id: 4,
      name: "Brown",
      cssClassName: "brown"
    },
    {
      id: 5,
      name: "Other",
      cssClassName: "other"
    }
  ],
  allSeals: [],
  allSortingOptions: [],
  allSellerTypes: [
    {
      id: "P",
      name: "Private"
    },
    {
      id: "D",
      name: "Dealer"
    }
  ],
  allPowerTypes: [
    {
      id: "hp",
      name: "hp"
    },
    {
      id: "kw",
      name: "kW"
    }
  ],
  allPaintWorks: [
    {
      id: "M",
      name: "Metallic"
    }
  ],
  allPreviousOwnersIds: [
    {
      id: 1,
      name: "1"
    },
    {
      id: 2,
      name: "2"
    },
    {
      id: 3,
      name: "3"
    },
    {
      id: 4,
      name: "> 3"
    }
  ],
  allBedTypeIds: {
    bedTypes: [
      {
        id: 206,
        name: "Bed"
      },
      {
        id: 207,
        name: "Lift bed"
      },
      {
        id: 208,
        name: "Bunk bed"
      }
    ],
    label: "Types of bed"
  },
  consumption: {
    consumptionIds: [3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15],
    label: "Kraftstoffverbrauch (komb.)"
  },
  metrics: {
    enabled: true,
    endpoint:
      "https://js-error-logger.infinity.eu-west-1.s24cloud.net/metrics/timeseries",
    prefix: "classified-list",
    tags: ["route:home-search-mask"]
  },
  behavior: {
    urlHistory: false,
    exactlyOneFilterGroupActive: false,
    searchSubscription: {
      anonymousSubscriptionEnabled: false,
      loggedInSaveSearchEnabled: false,
      showDoiInformationMessageEnabled: false
    },
    searchSubscriptionNotification: {
      enabled: false,
      level: "Information"
    },
    trackSelectedPriceAndRate: true,
    updateTotalCountAfterLoadingPersistedState: true
  },
  isSSR: false,
  pageSize: null,
  seo: {
    enabled: false,
    path: "/lst/"
  },
  recommender: {
    visitorId: null,
    itemId: null
  },
  initialRoute: "home-search-mask",
  visitorId: null
};
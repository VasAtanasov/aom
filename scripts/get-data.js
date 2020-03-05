const variablesObj = require('./getvehiclevariablelist.json');
const fetch = require("node-fetch");
const jsdom = require("jsdom");
const {JSDOM} = jsdom;

const variablesList = variablesObj.Results;

const url = 'https://vpic.nhtsa.dot.gov/api/vehicles/getvehiclevariablevalueslist/';
const format = 'format=json';

async function GET(variableId) {
    let response = await fetch(url + variableId + "?" + format);
    return await response.json();
}

const forLoop = async _ => {
    console.log('Start');

    for (let index = 0; index < variablesObj.Results.length; index++) {

        console.log(variablesObj.Results[index]["Name"]);

        const variableId = variablesObj.Results[index]["ID"];
        const result = await GET(variableId).then(data => {
            return data;
        });

        console.log(result)

    }

    console.log('End');
};

// forLoop().then(res => {
//     console.log(res)
// });


const getModelsAndBrands = async _ => {

    const getModelsJson = async function (id) {
        let response = await fetch(`https://www.cars.bg/?ajax=multimodel&brandId=${id}`);
        return await response.text();
    };

    const dom = new JSDOM("<!DOCTYPE html>\n" +
        "<html lang=\"en\">\n" +
        "<head>\n" +
        "    <meta charset=\"UTF-8\">\n" +
        "    <title>Title</title>\n" +
        "</head>\n" +
        "<body>\n" +
        '<select name="brandId" id="brandId">\n' +
        '    <option value="0">- Изберете -</option>\n' +
        '    <option value="1">Acura</option>\n' +
        '    <option value="2">Aixam</option>\n' +
        '    <option value="4">Alfa Romeo</option>\n' +
        '    <option value="5">Alpina</option>\n' +
        '    <option value="3">Aro</option>\n' +
        '    <option value="6">Asia Motors</option>\n' +
        '    <option value="7">Aston Martin</option>\n' +
        '    <option value="8">Audi</option>\n' +
        '    <option value="128">Austin</option>\n' +
        '    <option value="136">Barkas</option>\n' +
        '    <option value="135">Bedford</option>\n' +
        '    <option value="9">Bentley</option>\n' +
        '    <option value="10">BMW</option>\n' +
        '    <option value="104">Borgward</option>\n' +
        '    <option value="132">Bova</option>\n' +
        '    <option value="11">Brilliance</option>\n' +
        '    <option value="12">Bugatti</option>\n' +
        '    <option value="13">Buick</option>\n' +
        '    <option value="14">Cadillac</option>\n' +
        '    <option value="133">Carthago</option>\n' +
        '    <option value="15">Chevrolet</option>\n' +
        '    <option value="16">Chrysler</option>\n' +
        '    <option value="17">Citroen</option>\n' +
        '    <option value="18">Corvette</option>\n' +
        '    <option value="19">Dacia</option>\n' +
        '    <option value="20">Daewoo</option>\n' +
        '    <option value="93">DAF</option>\n' +
        '    <option value="21">Daihatsu</option>\n' +
        '    <option value="22">Datsun</option>\n' +
        '    <option value="23">Dodge</option>\n' +
        '    <option value="123">DR</option>\n' +
        '    <option value="140">DS</option>\n' +
        '    <option value="143">Eos</option>\n' +
        '    <option value="96">Excalibur</option>\n' +
        '    <option value="24">Ferrari</option>\n' +
        '    <option value="25">Fiat</option>\n' +
        '    <option value="26">Ford</option>\n' +
        '    <option value="111">Foton</option>\n' +
        '    <option value="127">Gac Gonow</option>\n' +
        '    <option value="27">GAZ</option>\n' +
        '    <option value="28">Geo</option>\n' +
        '    <option value="124">Gillet</option>\n' +
        '    <option value="29">GMC</option>\n' +
        '    <option value="30">Great wall</option>\n' +
        '    <option value="137">Haval</option>\n' +
        '    <option value="110">HIGER</option>\n' +
        '    <option value="31">Honda</option>\n' +
        '    <option value="103">Humber</option>\n' +
        '    <option value="32">Hummer</option>\n' +
        '    <option value="33">Hyundai</option>\n' +
        '    <option value="117">Ikarus</option>\n' +
        '    <option value="34">Infiniti</option>\n' +
        '    <option value="35">Isuzu</option>\n' +
        '    <option value="97">Iveco</option>\n' +
        '    <option value="36">Jaguar</option>\n' +
        '    <option value="37">Jeep</option>\n' +
        '    <option value="38">Kia</option>\n' +
        '    <option value="39">Koenigsegg</option>\n' +
        '    <option value="121">Ktm</option>\n' +
        '    <option value="40">Lada</option>\n' +
        '    <option value="41">Lamborghini</option>\n' +
        '    <option value="42">Lancia</option>\n' +
        '    <option value="43">Land Rover</option>\n' +
        '    <option value="44">Landwind</option>\n' +
        '    <option value="131">Latvia</option>\n' +
        '    <option value="112">LDV</option>\n' +
        '    <option value="45">Lexus</option>\n' +
        '    <option value="46">Ligier</option>\n' +
        '    <option value="47">Lincoln</option>\n' +
        '    <option value="48">Lotus</option>\n' +
        '    <option value="49">Mahindra</option>\n' +
        '    <option value="125">MAN</option>\n' +
        '    <option value="122">Martin Motors</option>\n' +
        '    <option value="50">Maserati</option>\n' +
        '    <option value="51">Matra</option>\n' +
        '    <option value="52">Maybach</option>\n' +
        '    <option value="53">Mazda</option>\n' +
        '    <option value="126">McLaren</option>\n' +
        '    <option value="54">Mercedes-Benz</option>\n' +
        '    <option value="55">MG</option>\n' +
        '    <option value="105">Microcar</option>\n' +
        '    <option value="56">Mini</option>\n' +
        '    <option value="57">Mitsubishi</option>\n' +
        '    <option value="58">Morgan</option>\n' +
        '    <option value="59">Moskvich</option>\n' +
        '    <option value="120">Neoplan</option>\n' +
        '    <option value="60">Nissan</option>\n' +
        '    <option value="109">NSU</option>\n' +
        '    <option value="61">Oldsmobile</option>\n' +
        '    <option value="62">Oltsit</option>\n' +
        '    <option value="63">Opel</option>\n' +
        '    <option value="64">Peugeot</option>\n' +
        '    <option value="65">Piaggio</option>\n' +
        '    <option value="66">Plymouth</option>\n' +
        '    <option value="67">Pontiac</option>\n' +
        '    <option value="68">Porsche</option>\n' +
        '    <option value="141">Proton</option>\n' +
        '    <option value="106">Range Rover</option>\n' +
        '    <option value="69">Renault</option>\n' +
        '    <option value="70">Rolls Royce</option>\n' +
        '    <option value="71">Rover</option>\n' +
        '    <option value="92">Saab</option>\n' +
        '    <option value="101">Samand</option>\n' +
        '    <option value="113">Santana</option>\n' +
        '    <option value="119">Scania</option>\n' +
        '    <option value="95">Scion</option>\n' +
        '    <option value="72">Seat</option>\n' +
        '    <option value="100">Setra</option>\n' +
        '    <option value="99">Shuanghuan</option>\n' +
        '    <option value="73">Skoda</option>\n' +
        '    <option value="74">Smart</option>\n' +
        '    <option value="75">Ssangyong</option>\n' +
        '    <option value="98">STOEWER</option>\n' +
        '    <option value="76">Subaru</option>\n' +
        '    <option value="77">Suzuki</option>\n' +
        '    <option value="78">Talbot</option>\n' +
        '    <option value="79">Tata</option>\n' +
        '    <option value="108">Tempo</option>\n' +
        '    <option value="118">Temsa</option>\n' +
        '    <option value="129">Tesla</option>\n' +
        '    <option value="80">Toyota</option>\n' +
        '    <option value="81">Trabant</option>\n' +
        '    <option value="82">Triumph</option>\n' +
        '    <option value="83">TVR</option>\n' +
        '    <option value="89">UAZ</option>\n' +
        '    <option value="142">Van Hool</option>\n' +
        '    <option value="84">Volga</option>\n' +
        '    <option value="85">Volvo</option>\n' +
        '    <option value="116">Vromos</option>\n' +
        '    <option value="86">VW</option>\n' +
        '    <option value="87">Wartburg</option>\n' +
        '    <option value="88">Wiesmann</option>\n' +
        '    <option value="134">Yogomo</option>\n' +
        '    <option value="90">Zastava</option>\n' +
        '    <option value="91">Zaz</option>\n' +
        '    <option value="107">ВАЗ</option>\n' +
        '    <option value="102">Варшава</option>\n' +
        '    <option value="130">София</option>\n' +
        '</select>' +
        "</body>\n" +
        "</html>");

    const options = dom.window.document.querySelectorAll("option");

    const carsModelsBrands = [];

    options.forEach(opt => {
        const obj = {};
        obj["id"] = opt.value;
        obj["brandName"] = opt.textContent;
        obj["models"] = [];
        carsModelsBrands.push(obj)
    });

    for (const obj of carsModelsBrands) {
        const response = await getModelsJson(obj.id).then(res => {
            const dom = new JSDOM("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                res + "\n" +
                "</body>\n" +
                "</html>");


            dom.window.document.querySelectorAll("input").forEach(inp => {
                if (inp.classList.contains("model")) {
                    obj.models.push(inp.dataset["label"]);
                }
            });
        });
    }


    require('fs').writeFile(

        './make_models.json',

        JSON.stringify(carsModelsBrands),

        function (err) {
            if (err) {
                console.error('Crap happens');
            }
        }
    );
};

getModelsAndBrands();





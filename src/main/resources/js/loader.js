const axios = require('axios');
const cheerio = require('cheerio');

const readTableFromUrl = async (url) => {
    let target = false;
    try {
        const response = await axios.get(url);
        const $ = cheerio.load(response.data);

        const tables = $('table');
        target = findSpecified(tables, $);
        if (target) {
            return parseTable(target, $);
        }
    } catch (error) {
        throw new Error(`Error occurred: ${error.message}`);
    }
    throw new Error('Specified table not found');


};

const findSpecified = (tables, $) => {
    let specifiedTable = null;
    tables.each((index, table) => {
        const caption = $(table).find('caption').text();
        if (caption.includes('Trigger scopes')) {
            specifiedTable = table;
            return false; // Break the loop
        }
    });
    return specifiedTable;
};

const parseTable = (table, $) => {
    const rows = $(table).find('tr');
    const tableData = [];

    rows.each((index, row) => {
        const cols = $(row).find('th, td').map((i, col) => $(col).text().trim()).get();
        tableData.push(cols);
    });

    return tableData;
};

// Example usage
const url = 'https://hoi4.paradoxwikis.com/Triggers';
readTableFromUrl(url)
    .then(tableData => console.log(tableData))
    .catch(error => console.error(error));
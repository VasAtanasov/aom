// arrayOfObj is array of objects representing entities where keys are column names and their values
// call loadData method passing the name of the table and the array of objects

class InsertGenerator {
    constructor(tableName, arrayOfObj) {
        this.tableName = tableName;
        this.arrayOfObj = arrayOfObj
    }

    generateArrays(entityObj) {
        const columnNames = [];
        const columnValues = [];

        Object.keys(entityObj).forEach(column => {
            columnNames.push(column);
            columnValues.push(entityObj[column])
        });

        return {
            columnNames,
            columnValues,
        }
    };

    getTableColumns(columnNamesArray) {
        return `(${columnNamesArray.join(", ")})`;
    };

    getValuesColumns(columnNamesArray) {
        return `(${columnNamesArray.map(value => {
            if (isNaN(value)) {
                return `'${value}'`
            }
            return value;
        }).join(", ")})`;
    };

    generateStatement(tableName, columns, values) {
        return `insert into ${tableName} ${columns} values ${values};`
    };

    generateDataSql() {
        const dataSql = [];
        this.arrayOfObj.forEach(entity => {
            const arrays = this.generateArrays(entity);

            const columns = this.getTableColumns(arrays.columnNames);
            const values = this.getValuesColumns(arrays.columnValues);

            const statement = this.generateStatement(this.tableName, columns, values);

            dataSql.push(statement);
        });

        return dataSql;
    };

    getSqlString() {
        return this.generateDataSql().join("\n");
    }
}

module.exports = {
    InsertGenerator
};

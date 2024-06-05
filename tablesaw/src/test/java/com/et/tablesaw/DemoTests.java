package com.et.tablesaw;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.context.junit4.SpringRunner;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.api.AreaPlot;
import tech.tablesaw.plotly.Plot;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());
    Table tornadoes;

    @Before
    public void before() throws IOException {
        log.info("init some data");
        tornadoes = Table.read().csv("D:\\IdeaProjects\\ETFramework\\tablesaw\\src\\main\\resources\\data\\tornadoes_1950-2014.csv");

    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void execute()  {
        log.info("your method test Code");
    }

    @Test
    public void columnNames() throws IOException {
        System.out.println(tornadoes.columnNames());
    }
    @Test
    public void shape() throws IOException {
        System.out.println(tornadoes.shape());
    }
    @Test
    public void structure() throws IOException {
        System.out.println(tornadoes.structure().printAll());
    }
    @Test
    public void show() throws IOException {
        System.out.println(tornadoes);
    }
    @Test
    public void structurefilter() throws IOException {
        System.out.println( tornadoes
                .structure()
                .where(tornadoes.structure().stringColumn("Column Type").isEqualTo("DOUBLE")));

    }
    @Test
    public void previewdata() throws IOException {
        System.out.println(tornadoes.first(3));

    }
    @Test
    public void ColumnOperate() throws IOException {
        StringColumn month = tornadoes.dateColumn("Date").month();
        tornadoes.addColumns(month);
        System.out.println(tornadoes.first(3));
        tornadoes.removeColumns("State No");
        System.out.println(tornadoes.first(3));

    }
    @Test
    public void sort() throws IOException {
        tornadoes.sortOn("-Fatalities");
        System.out.println(tornadoes.first(20));


    }
    @Test
    public void summary() throws IOException {
        System.out.println( tornadoes.column("Fatalities").summary().print());

    }
    @Test
    public void filter() throws IOException {
        Table result = tornadoes.where(tornadoes.intColumn("Fatalities").isGreaterThan(0));
        result = tornadoes.where(result.dateColumn("Date").isInApril());
        result =
                tornadoes.where(
                        result
                                .intColumn("Width")
                                .isGreaterThan(300) // 300 yards
                                .or(result.doubleColumn("Length").isGreaterThan(10))); // 10 miles

        result = result.select("State", "Date");
        System.out.println(result);
    }
    @Test
    public void write() throws IOException {
        tornadoes.write().csv("rev_tornadoes_1950-2014-test.csv");

    }
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Test
    public void datafrommysql() throws IOException {
        Table table = jdbcTemplate.query("SELECT  user_id,username,age from user_info", new ResultSetExtractor<Table>() {
            @Override
            public Table extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                return Table.read().db(resultSet);
            }
        });
        System.out.println(table);
    }



}


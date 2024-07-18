package simpleCRUD.unitTest;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import simpleCRUD.unitTest.controller.TransactionControllerTest;
import simpleCRUD.unitTest.service.*;


@Suite
@SelectClasses({
        TransactionControllerTest.class,
        CustomerServiceTest.class,
        TransactionServiceTest.class
})
public class TestSuite {
}

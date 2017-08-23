import com.orient.task.LoadData12345;
import com.orient.task.LoadDataYGXF;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by sunweipeng on 2017/7/28.
 */
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DownloadDataTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void test12345() throws IOException, InterruptedException {
        LoadData12345 loadData =  applicationContext.getBean("loadData12345", LoadData12345.class);
        loadData.runTask();
    }

    @Test
    public void testYGXF() throws IOException, InterruptedException {
        LoadDataYGXF loadData =  applicationContext.getBean("loadDataYGXF", LoadDataYGXF.class);
        loadData.runTask();
    }
}

import com.orient.ApplicationDestoryListener;
import com.orient.BackStepClient;
import com.orient.task.LoadData12345;
import com.orient.task.LoadDataYGXF;
import net.sf.json.JSONObject;
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
public class RestClientTest {

    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void test12345() throws IOException, InterruptedException {
        BackStepClient backStepClient =  applicationContext.getBean("backStepClient", BackStepClient.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ddd","fdfdf");
        backStepClient.submit();
    }

}

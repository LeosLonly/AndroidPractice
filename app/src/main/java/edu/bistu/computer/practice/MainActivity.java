package edu.bistu.computer.practice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final static String PRODUCT = "product";
    private final static String PRICE = "price";
    private final static String CONFIGURATION = "configuration";
    // private CoordinatorLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Button button = (Button) findViewById(R.id.button);
        container = (CoordinatorLayout) findViewById(R.id.container);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(container,"SnackbarTest",Snackbar.LENGTH_SHORT).show();
            }
        });*/

        String[] products = {"小米", "华为", "魅族", "锤子" };
        String[] prices = {"2000", "2500", "3000", "3500" };
        String[] configurations = {"高通骁龙 801，3GB RAM，16GB ROM", "麒麟 935，3GB RAM，16GB ROM",
                "联发科（MTK)Helio X10 Turbo，3GB RAM，32GB ROM", "高通骁龙 801，2GB RAM，32GB ROM" };

        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < products.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put(PRODUCT, products[i]);
            item.put(PRICE, prices[i]);
            item.put(CONFIGURATION, configurations[i]);
            items.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.item,
                new String[]{PRODUCT, PRICE, CONFIGURATION},
                new int[]{R.id.product_name, R.id.product_price, R.id.product_configuration});

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}

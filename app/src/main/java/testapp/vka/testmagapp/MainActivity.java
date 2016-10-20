package testapp.vka.testmagapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import testapp.vka.testmagapp.Model.ApiService;
import testapp.vka.testmagapp.Model.Category;
import testapp.vka.testmagapp.Model.Offer;
import testapp.vka.testmagapp.Model.ResponseModel;
import testapp.vka.testmagapp.gui.ConnectionErrorDialog;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CategoriesFragment.OnChooseCategoryListener {

    public static final String DATA_ARG = "DATA_ARG";
    private ResponseModel responseModel;
    private static Observable<ResponseModel> observableResponse = ApiService.getInstance().getApi().getResponceModel().asObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .cache();
    private Subscription subscription;
    @BindView(R.id.progress_bar)
    ProgressBar circularProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        sendRequest();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void sendRequest() {
        subscription = observableResponse.subscribe(responseModel -> {
            this.responseModel = responseModel;
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                drawFragment(CategoriesFragment.newInstance(responseModel.getCategories()));
            }
            circularProgress.setVisibility(View.GONE);
        }, error -> showErrorDialog());
    }

    public void drawFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int count = getSupportFragmentManager().getBackStackEntryCount() - 1;

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (count == 0) {
                getSupportFragmentManager().popBackStack();
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_catalog) {
            drawFragment(CategoriesFragment.newInstance(responseModel.getCategories()));
        } else if (id == R.id.nav_contacts) {
            drawFragment(new ContactFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onChooseCategory(Category category, int position) {
        ArrayList<Offer> offers = responseModel.getOfferByCategory(category.getId());
        drawFragment(OffersListFragment.newInstance(offers, category.getName()));
    }

    public void showErrorDialog() {
        ConnectionErrorDialog dialog = new ConnectionErrorDialog();
        dialog.setListener(this::onRestart);
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }
}

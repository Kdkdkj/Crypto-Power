package com.itbpower.crytocoins.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itbpower.crytocoins.R;
import com.itbpower.crytocoins.SpeedTestThread.DownloadTest;
import com.itbpower.crytocoins.SpeedTestThread.HttpUploadTest;
import com.itbpower.crytocoins.SpeedTestThread.PingTest;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;





public class SpeedTestActivity extends AppCompatActivity {
    LinearLayout graph_chart;
    GetSpeedTestHandler getSpeedTestHandler;
    HashSet<String> tempBlackList;
    static int position = 0;
    static int lastPosition = 0;
    RotateAnimation rotate;
    Context context;
    Button test_button;
    TextView ping_text;
    TextView down_text;
    TextView upl_text;
    ImageView bar_speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_test);
        initViews();
        context = this;
        tempBlackList = new HashSet<>();
        getSpeedTestHandler = new GetSpeedTestHandler();
        getSpeedTestHandler.start();
        if (test_button != null)
            test_button.setOnFocusChangeListener(new SpeedTestActivity.OnFocusChangeAccountListener(test_button));
        initialize();
    }

    private void initViews() {
        test_button=findViewById(R.id.test_button);
        ping_text=findViewById(R.id.ping_text);
        down_text=findViewById(R.id.down_text);
        upl_text=findViewById(R.id.upl_text);
        bar_speed=findViewById(R.id.bar_speed);
        graph_chart=findViewById(R.id.graph_chart);

    }

    private void initialize() {
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test_button.setEnabled(false);
                testCheckSpeed();

            }
        });

    }

    private void testCheckSpeed() {
        if (getSpeedTestHandler == null) {
            getSpeedTestHandler = new GetSpeedTestHandler();
            getSpeedTestHandler.start();
        }
        graph_chart.setVisibility(View.VISIBLE);
        final DecimalFormat dec = new DecimalFormat("#.##");
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            test_button.setText(getResources().getString(R.string.ping_based));
                        }
                    });
                    int timecount = 600;
                    while (!getSpeedTestHandler.isFinished()) {
                        timecount--;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                        }
                        if (timecount <= 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "No Connection...", Toast.LENGTH_LONG).show();
                                    test_button.setEnabled(true);
//                                test_button.setTextSize(16);
                                    test_button.setText(getResources().getString(R.string.restart_test));
                                }
                            });
                            getSpeedTestHandler = null;
                            return;
                        }
                    }
                    HashMap<Integer, String> mKey = getSpeedTestHandler.getKeyMap();
                    HashMap<Integer, List<String>> vKey = getSpeedTestHandler.getValueMap();
                    double latSelf = getSpeedTestHandler.getLatSelf();
                    double lonSelf = getSpeedTestHandler.getLonSelf();
                    double tmp = 19349458;
                    double dist = 0.0;
                    int findServerIndex = 0;
                    for (int index : mKey.keySet()) {
                        if (tempBlackList.contains(vKey.get(index).get(5))) {
                            continue;
                        }
                        Location source = new Location("Source");
                        source.setLatitude(latSelf);
                        source.setLongitude(lonSelf);

                        List<String> list = vKey.get(index);
                        Location dest = new Location("Dest");
                        dest.setLatitude(Double.parseDouble(list.get(0)));
                        dest.setLongitude(Double.parseDouble(list.get(1)));

                        double distance = source.distanceTo(dest);
                        if (tmp > distance) {
                            tmp = distance;
                            dist = distance;
                            findServerIndex = index;
                        }
                    }
                    String uploadAddr = mKey.get(findServerIndex);
                    List<String> info = vKey.get(findServerIndex);
                    final double distance = dist;

                    if (info == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                            test_button.setTextSize(12);
                                test_button.setText(getResources().getString(R.string.problem_host));
                            }
                        });
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        test_button.setTextSize(13);
                            test_button.setText(String.format("Host Location: %s [Distance: %s km]", info.get(2), new DecimalFormat("#.##").format(distance / 1000)));
                        }
                    });

                    LinearLayout ping = (LinearLayout) findViewById(R.id.ping);
                    XYSeriesRenderer pingRenderer = new XYSeriesRenderer();
                    XYSeriesRenderer.FillOutsideLine pingFill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                    pingFill.setColor(Color.parseColor("#4d5a6a"));
                    pingRenderer.addFillOutsideLine(pingFill);
                    pingRenderer.setDisplayChartValues(false);
                    pingRenderer.setShowLegendItem(false);
//                pingRenderer.setColor(Color.parseColor("#4d5a6a"));
                    pingRenderer.setColor(Color.parseColor("#04D9F5"));
                    pingRenderer.setLineWidth(5);
                    final XYMultipleSeriesRenderer multiPingRenderer = new XYMultipleSeriesRenderer();
                    multiPingRenderer.setXLabels(0);
                    multiPingRenderer.setYLabels(0);
                    multiPingRenderer.setZoomEnabled(false);
                    multiPingRenderer.setXAxisColor(Color.parseColor("#647488"));
                    multiPingRenderer.setYAxisColor(Color.parseColor("#2F3C4C"));
                    multiPingRenderer.setPanEnabled(true, true);
                    multiPingRenderer.setZoomButtonsVisible(false);
                    multiPingRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
                    multiPingRenderer.addSeriesRenderer(pingRenderer);

                    //Init Download graphic
                    final LinearLayout chartDownload = (LinearLayout) findViewById(R.id.download);
                    XYSeriesRenderer downloadRenderer = new XYSeriesRenderer();
                    XYSeriesRenderer.FillOutsideLine downloadFill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                    downloadFill.setColor(Color.parseColor("#4d5a6a"));
                    downloadRenderer.addFillOutsideLine(downloadFill);
                    downloadRenderer.setDisplayChartValues(false);
//                downloadRenderer.setColor(Color.parseColor("#4d5a6a"));
                    downloadRenderer.setColor(Color.parseColor("#04D9F5"));
                    downloadRenderer.setShowLegendItem(false);
                    downloadRenderer.setLineWidth(5);
                    final XYMultipleSeriesRenderer multiDownloadRenderer = new XYMultipleSeriesRenderer();
                    multiDownloadRenderer.setXLabels(0);
                    multiDownloadRenderer.setYLabels(0);
                    multiDownloadRenderer.setZoomEnabled(false);
                    multiDownloadRenderer.setXAxisColor(Color.parseColor("#647488"));
                    multiDownloadRenderer.setYAxisColor(Color.parseColor("#2F3C4C"));
                    multiDownloadRenderer.setPanEnabled(false, false);
                    multiDownloadRenderer.setZoomButtonsVisible(false);
                    multiDownloadRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
                    multiDownloadRenderer.addSeriesRenderer(downloadRenderer);

                    //Init Upload graphic
                    final LinearLayout chartUpload = (LinearLayout) findViewById(R.id.upload);
                    XYSeriesRenderer uploadRenderer = new XYSeriesRenderer();
                    XYSeriesRenderer.FillOutsideLine uploadFill = new XYSeriesRenderer.FillOutsideLine(XYSeriesRenderer.FillOutsideLine.Type.BOUNDS_ALL);
                    uploadFill.setColor(Color.parseColor("#4d5a6a"));
                    uploadRenderer.addFillOutsideLine(uploadFill);
                    uploadRenderer.setDisplayChartValues(false);
//                uploadRenderer.setColor(Color.parseColor("#4d5a6a"));
                    uploadRenderer.setColor(Color.parseColor("#04D9F5"));
                    uploadRenderer.setShowLegendItem(false);
                    uploadRenderer.setLineWidth(5);
                    final XYMultipleSeriesRenderer multiUploadRenderer = new XYMultipleSeriesRenderer();
                    multiUploadRenderer.setXLabels(0);
                    multiUploadRenderer.setYLabels(0);
                    multiUploadRenderer.setZoomEnabled(false);
                    multiUploadRenderer.setXAxisColor(Color.parseColor("#647488"));
                    multiUploadRenderer.setYAxisColor(Color.parseColor("#2F3C4C"));
                    multiUploadRenderer.setPanEnabled(false, false);
                    multiUploadRenderer.setZoomButtonsVisible(false);
                    multiUploadRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
                    multiUploadRenderer.addSeriesRenderer(uploadRenderer);

                    //Reset value, graphics
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ping_text.setText("0 ms");
                            ping.removeAllViews();
                            down_text.setText("0 Mbps");
                            chartDownload.removeAllViews();
                            upl_text.setText("0 Mbps");
                            chartUpload.removeAllViews();
                        }
                    });
                    final List<Double> pingRateList = new ArrayList<>();
                    final List<Double> downloadRateList = new ArrayList<>();
                    final List<Double> uploadRateList = new ArrayList<>();
                    Boolean pingTestStarted = false;
                    Boolean pingTestFinished = false;
                    Boolean downloadTestStarted = false;
                    Boolean downloadTestFinished = false;
                    Boolean uploadTestStarted = false;
                    Boolean uploadTestFinished = false;
                    //Init Test

                    final PingTest pingTest = new PingTest(info.get(6).replace(":8080", ""), 6);
                    final DownloadTest downloadTest = new DownloadTest(uploadAddr.replace(uploadAddr.split("/")[uploadAddr.split("/").length - 1], ""));
                    final HttpUploadTest uploadTest = new HttpUploadTest(uploadAddr);

                    //Tests
                    while (true) {
                        if (!pingTestStarted) {
                            pingTest.start();
                            pingTestStarted = true;
                        }
                        if (pingTestFinished && !downloadTestStarted) {
                            downloadTest.start();
                            downloadTestStarted = true;
                        }
                        if (downloadTestFinished && !uploadTestStarted) {
                            uploadTest.start();
                            uploadTestStarted = true;
                        }
                        //Ping Test
                        if (pingTestFinished) {
                            //Failure
                            if (pingTest.getAvgRtt() == 0) {
                                System.out.println("Ping error...");
                            } else {
                                //Success
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ping_text.setText(dec.format(pingTest.getAvgRtt()) + " ms");
                                    }
                                });
                            }
                        } else {
                            pingRateList.add(pingTest.getInstantRtt());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ping_text.setText(dec.format(pingTest.getInstantRtt()) + " ms");
                                }
                            });
                            //Update chart
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Creating an  XYSeries for Income
                                    XYSeries pingSeries = new XYSeries("");
                                    pingSeries.setTitle("");

                                    int count = 0;
                                    List<Double> tmpLs = new ArrayList<>(pingRateList);
                                    for (Double val : tmpLs) {
                                        pingSeries.add(count++, val);
                                    }

                                    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                                    dataset.addSeries(pingSeries);

                                    GraphicalView chartView = ChartFactory.getLineChartView(getBaseContext(), dataset, multiPingRenderer);
                                    ping.addView(chartView, 0);

                                }
                            });
                        }
                        //Download Test
                        if (pingTestFinished) {
                            if (downloadTestFinished) {
                                //Failure
                                if (downloadTest.getFinalDownloadRate() == 0) {
                                    System.out.println("Download error...");
                                } else {
                                    //Success
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            down_text.setText(dec.format(downloadTest.getFinalDownloadRate()) + " Mbps");
                                        }
                                    });
                                }
                            } else {
                                //Calc position
                                double downloadRate = downloadTest.getInstantDownloadRate();
                                downloadRateList.add(downloadRate);
                                position = getPositionByRate(downloadRate);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rotate = new RotateAnimation(lastPosition, position, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                        rotate.setInterpolator(new LinearInterpolator());
                                        rotate.setDuration(100);
                                        bar_speed.startAnimation(rotate);
                                        down_text.setText(dec.format(downloadTest.getInstantDownloadRate()) + " Mbps");

                                    }

                                });
                                lastPosition = position;

                                //Update chart
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Creating an  XYSeries for Income
                                        XYSeries downloadSeries = new XYSeries("");
                                        downloadSeries.setTitle("");

                                        List<Double> tmpLs = new ArrayList<>(downloadRateList);
                                        int count = 0;
                                        for (Double val : tmpLs) {
                                            downloadSeries.add(count++, val);
                                        }

                                        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                                        dataset.addSeries(downloadSeries);

                                        GraphicalView chartView = ChartFactory.getLineChartView(getBaseContext(), dataset, multiDownloadRenderer);
                                        chartDownload.addView(chartView, 0);
                                    }
                                });

                            }
                        }
                        if (downloadTestFinished) {
                            if (uploadTestFinished) {
                                //Failure
                                if (uploadTest.getFinalUploadRate() == 0) {
                                    System.out.println("Upload error...");
                                } else {
                                    //Success
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            upl_text.setText(dec.format(uploadTest.getFinalUploadRate()) + " Mbps");
                                        }
                                    });
                                }
                            } else {
                                //Calc position
                                double uploadRate = uploadTest.getInstantUploadRate();
                                uploadRateList.add(uploadRate);
                                position = getPositionByRate(uploadRate);

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        rotate = new RotateAnimation(lastPosition, position, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                        rotate.setInterpolator(new LinearInterpolator());
                                        rotate.setDuration(100);
                                        bar_speed.startAnimation(rotate);
                                        upl_text.setText(dec.format(uploadTest.getInstantUploadRate()) + " Mbps");
                                    }

                                });
                                lastPosition = position;

                                //Update chart
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        // Creating an  XYSeries for Income
                                        XYSeries uploadSeries = new XYSeries("");
                                        uploadSeries.setTitle("");

                                        int count = 0;
                                        List<Double> tmpLs = new ArrayList<>(uploadRateList);
                                        for (Double val : tmpLs) {
                                            if (count == 0) {
                                                val = 0.0;
                                            }
                                            uploadSeries.add(count++, val);
                                        }

                                        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                                        dataset.addSeries(uploadSeries);

                                        GraphicalView chartView = ChartFactory.getLineChartView(getBaseContext(), dataset, multiUploadRenderer);
                                        chartUpload.addView(chartView, 0);
                                    }
                                });

                            }
                        }
                        //Test bitti
                        if (pingTestFinished && downloadTestFinished && uploadTest.isFinished()) {
                            break;
                        }

                        if (pingTest.isFinished()) {
                            pingTestFinished = true;
                        }
                        if (downloadTest.isFinished()) {
                            downloadTestFinished = true;
                        }
                        if (uploadTest.isFinished()) {
                            uploadTestFinished = true;
                        }

                        if (pingTestStarted && !pingTestFinished) {
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                            }
                        } else {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                            }
                        }
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            test_button.setEnabled(true);
//                          test_button.setTextSize(12);
                            test_button.setText(getResources().getString(R.string.restart_test));
                        }
                    });
                }


            }).start();
        } catch (Exception e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public int getPositionByRate(double rate) {
        if (rate <= 1) {
            return (int) (rate * 30);

        } else if (rate <= 10) {
            return (int) (rate * 6) + 30;

        } else if (rate <= 30) {
            return (int) ((rate - 10) * 3) + 90;

        } else if (rate <= 50) {
            return (int) ((rate - 30) * 1.5) + 150;

        } else if (rate <= 100) {
            return (int) ((rate - 50) * 1.2) + 180;
        }

        return 0;
    }

    private class OnFocusChangeAccountListener implements View.OnFocusChangeListener {

        private final View view;

        public OnFocusChangeAccountListener(View view) {
            this.view = view;

        }

        @SuppressLint("ResourceType")
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (hasFocus) {
                float to = hasFocus ? 1.12f : 1;
                performScaleXAnimation(to);
                performScaleYAnimation(to);

                Log.e("id is", "" + view.getTag());

                if (view.getTag() != null && view.getTag().equals("1")) {
                    test_button.setBackgroundResource(R.drawable.blue_btn_effect);
                }

            } else if (!hasFocus) {
                float to = hasFocus ? 1.09f : 1;
                performScaleXAnimation(to);
                performScaleYAnimation(to);
                performAlphaAnimation(hasFocus);
                if (view != null && view.getTag() != null && view.getTag().equals("1")) {
                    test_button.setBackgroundResource(R.drawable.black_button_dark);
                }

            }
        }

        private void performScaleXAnimation(float to) {
            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX", to);
            scaleXAnimator.setDuration(150);
            scaleXAnimator.start();
        }

        private void performScaleYAnimation(float to) {
            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY", to);
            scaleYAnimator.setDuration(150);
            scaleYAnimator.start();
        }

        private void performAlphaAnimation(boolean hasFocus) {

            if (hasFocus) {
                float toAlpha = hasFocus ? 0.6f : 0.5f;
                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", toAlpha);
                alphaAnimator.setDuration(150);
                alphaAnimator.start();
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSpeedTestHandler = new GetSpeedTestHandler();
        getSpeedTestHandler.start();
    }
}


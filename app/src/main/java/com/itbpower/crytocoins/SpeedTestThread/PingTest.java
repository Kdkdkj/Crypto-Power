package com.itbpower.crytocoins.SpeedTestThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PingTest extends Thread {
    HashMap<String, Object> result = new HashMap<>();
    String server = "";
    int count;
    double instantRtt = 0;
    double avgRtt = 0.0;
    boolean finished = false;
    boolean started = false;

    public PingTest(String serverIpAddress, int pingTryCount) {
        this.server = serverIpAddress;
        this.count = pingTryCount;
    }

    public HashMap<String, Object> getResult() {
        return result;
    }

    public String getServer() {
        return server;
    }

    public int getCount() {
        return count;
    }

    public double getInstantRtt() {
        return instantRtt;
    }

    public double getAvgRtt() {
        return avgRtt;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder prs = new ProcessBuilder("ping", "-c" + count, this.server);
            prs.redirectErrorStream(true);

            Process pr = prs.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.contains("icmp_seq")) {
                    instantRtt = Double.parseDouble(line.split(" ")[line.split(" ").length - 2].replace("time=", ""));
                }
                if (line.startsWith("rtt ")) {
                    avgRtt = Double.parseDouble(line.split("/")[4]);
                    break;
                }
            }
            pr.waitFor();
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        finished = true;
    }
}

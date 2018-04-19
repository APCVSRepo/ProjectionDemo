package demo.projection.ford.com.projectiondemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by leon on 2018/3/21.
 */

public class Utils
{
    public enum VHAMsgType
    {
        ENGINE,
        EXHAUST,
        FUEL,
        STEER,
        TIRE,
    }

    public static class VHAMsg
    {


        public VHAMsg(int icon, String title, String detail)
        {
            this.icon = icon;
            this.title = title;
            this.detail = detail;
        }
        public int icon;
        public String title;
        public String detail;
    }

    private static final Map<VHAMsgType, List<VHAMsg>> VHAMSG_MAP = new HashMap<>();
    static
    {
        List<VHAMsg> engineList = new ArrayList<>();
        engineList.add(new VHAMsg(R.drawable.ic_low_engine_oil_pressure_large, "Low Engine Oil Pressure", "The engine oil pressure is low. Please stop your vehilce as soon as it is safe to do so and switch off the engine. Check the oil level and add oil if needed. If the warning persists, your engine may require service; please switch off the vehicle and contact an authorized dealer ASAP."));
        engineList.add(new VHAMsg(R.drawable.ic_malfunction_indicator_lamp_large, "Malfunction Indicator Lamp", "An engine malfunction has been detected."));

        List<VHAMsg> exhaustList = new ArrayList<>();
        exhaustList.add(new VHAMsg(R.drawable.ic_diesel_exhaust_fluid_system_fault_large,"Diesel Exhaust Fluid System Fault", "A contamination was detected in the diesel exhaust fluid. Catalytic reduction systems which serve to reduce exhaust emissions are sensitive to such contamination. "));
        exhaustList.add(new VHAMsg(R.drawable.ic_diesel_exhaust_over_temperature_yellow_large, "Diesel Exhaust Over-temperature", "The vehicle exhaust system temperature exceeds intended operating range. If Stop Safely Now displays and a chime sounds, engine power is reduced and the engine will shut down below 3mph (5 km/h) and may not restart, depending on the severity of the condition. "));
        exhaustList.add(new VHAMsg(R.drawable.ic_diesel_particulate_filter_cleaning_yellow_large, "Diesel Particulate Filter Cleaning", "Your vehicle's exhaust filter is full of exhaust soot particles and the vehicle needs to be operated in a manner to allow automatic cleaning. This message will stay on until the exhaust filter cleaning has begun, at which time the Cleaning Exhaust Filter message is displayed. When this occurs, drive the vehicle above 30 mph (48 km/h) for at least 20 minutes until the Cleaning Exhaust Filter message turns off. This message is normal and does not indicate a malfunction. "));

        List<VHAMsg> fuelList = new ArrayList<>();
        fuelList.add(new VHAMsg(R.drawable.ic_check_fuel_cap_large, "Check Fuel Cap", "Your fuel cap may be loose. At the next safe opportunity, please pull off the road and reinstall it. If your vehicle has a capless fuel system, please inspect the fuel inlet to ensure it has closed properly."));
        fuelList.add(new VHAMsg(R.drawable.ic_low_fuel_large, "Low Fuel", "Your vehicle is low on fuel. (Please refer to the Fuel and Refuelling chapter in your Owner's Manual for more information."));
        fuelList.add(new VHAMsg(R.drawable.ic_water_in_fuel_large, "Water in Fuel", "Safely stop your vehicle as soon as possible, shut off the engine, then drain the Diesel Fuel Conditioner Module (DFCM)."));

        List<VHAMsg> steerList = new ArrayList<>();
        steerList.add(new VHAMsg(R.drawable.ic_service_steering_yellow_large, "Service Steering", "POWER STEERING ASSIST FAULT or STEERING MALFUNCTION STOP SAFELY: Stop vehicle in safe place and switch off for at least 10 seconds. Restart. If message returns, contact authorized dealer."));

        List<VHAMsg> tireList = new ArrayList<>();
        tireList.add(new VHAMsg(R.drawable.ic_antilock_brake_fault, "Antilock Brake Fault", "The anti-lock brake system has detected a malfunction. Please contact your authorized dealer ASAP."));
        tireList.add(new VHAMsg(R.drawable.ic_brake_warning, "Brake Warning", "Please ensure your vehicle's parking brake is released. If the light remains on, the brake system should be inspected ASAP by an authorized dealer."));
        tireList.add(new VHAMsg(R.drawable.ic_tire_pressure_monitor_system_warn_large, "Tire Pressure Monitor System Warning", "One or more of your tires may be significantly under-inflated or there is a tire pressure monitoring system malfunction. Please check your tire pressure ASAP."));

        VHAMSG_MAP.put(VHAMsgType.ENGINE, engineList);
        VHAMSG_MAP.put(VHAMsgType.EXHAUST, exhaustList);
        VHAMSG_MAP.put(VHAMsgType.FUEL, fuelList);
        VHAMSG_MAP.put(VHAMsgType.STEER, steerList);
        VHAMSG_MAP.put(VHAMsgType.TIRE, tireList);
    }


    public static int getRandomRange(int min, int max)
    {
        Random rand = new Random();
        int ret = min - 1;
        while(ret < min)
        {
            ret = rand.nextInt(max + 1);
        }

        return ret;
    }

    public static Set<Integer> getRandomSet(int min, int max, int count)
    {
        Set<Integer> set = new TreeSet<>();

        int i = 0;
        while(i < count)
        {
            int val = getRandomRange(min, max);
            if (!set.contains(val))
            {
                set.add(val);
                ++i;
            }
        }

        return set;
    }



    public static Map<VHAMsgType, List<VHAMsg>> generateVHAMessageList()
    {
        Map<VHAMsgType, List<VHAMsg>> map = new HashMap<>();

        Set<Integer> types = getRandomSet(0, VHAMSG_MAP.size()-1, getRandomRange(1, VHAMSG_MAP.size()));
        if (types != null)
        {
            for (int type : types)
            {
                List<VHAMsg> msgs = VHAMSG_MAP.get(VHAMsgType.values()[type]);
                Set<Integer> msgIndexs = getRandomSet(0, msgs.size()-1, getRandomRange(1, msgs.size()));

                List<VHAMsg> outMsgs = new ArrayList<>();
                for(int msgIndex : msgIndexs)
                {
                    outMsgs.add(msgs.get(msgIndex));
                }

                map.put(VHAMsgType.values()[type], outMsgs);
            }
        }

        return map;
    }

}

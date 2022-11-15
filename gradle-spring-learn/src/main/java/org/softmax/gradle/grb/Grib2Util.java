package org.softmax.gradle.grb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ucar.ma2.Array;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author Jarvis
 */
public class Grib2Util {

    private static final Logger log = LoggerFactory.getLogger(Grib2Util.class);
    private static final Logger gribLog = LoggerFactory.getLogger("grib");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public void readGrid() throws IOException {
        String gribPath = "D:\\20220530\\Z_NWGD_C_BEZZ_20220530051014_P_RFFC_SPCC-ER03_202205300800_24003.GRB2";
        String fpath="C:\\Users\\Jarvis\\Downloads\\wafsgfs_L_t06z_intdsk60.grib2";

        NetcdfFile ncf = NetcdfFile.open(fpath, null);
        System.out.println("Variable names are:");
        List<Variable> vars = ncf.getVariables();
        for (Variable var : vars) {
            System.out.println(var);
        }
    }

    public void cdf() throws IOException {
        //loading grib file
        NetcdfFile ncf = NetcdfFile.open("gribfilename.grb");
        System.out.println("Variable names are:");
        //listing variables
        List<Variable> vars = ncf.getVariables();
        for (Variable var : vars) {
            System.out.println(var.getName());
        }
        Variable Uwind = ncf.findVariable("u-component_of_wind_height_above_ground");
        Variable Vwind = ncf.findVariable("v-component_of_wind_height_above_ground");
        Variable lat = ncf.findVariable("lat");
        Variable lon = ncf.findVariable("lon");
        Variable time = ncf.findVariable("time");
        Variable reftime = ncf.findVariable("reftime");
        Variable reftime_ISO = ncf.findVariable("reftime_ISO");
        Variable height_above_ground = ncf.findVariable("height_above_ground");
        Variable height_above_ground1 = ncf.findVariable("height_above_ground1");
        Variable Temperature_height_above_ground = ncf.findVariable("Temperature_height_above_ground");
        Variable Pressure_reduced_to_MSL_msl = ncf.findVariable("Pressure_reduced_to_MSL_msl");


        Array u_data = Uwind.read(); //reading variables to Array type
        Array v_data = Vwind.read();
        Array lat_data = lat.read();
        Array lon_data = lon.read();
        Array time_data = time.read();
        Array reftime_data = reftime.read();
        Array reftime_ISO_data = reftime_ISO.read();
        Array height_above_ground_data = height_above_ground.read();
        Array height_above_ground1_data = height_above_ground1.read();
        Array Temperature_height_above_ground_data = Temperature_height_above_ground.read();
        Array Pressure_reduced_to_MSL_msl_data = Pressure_reduced_to_MSL_msl.read();

        ncf.close();
    }

}

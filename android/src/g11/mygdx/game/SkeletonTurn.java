package g11.mygdx.game;

/*
 * Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Basic turn data. It's just a blank data string and a turn number counter.
 *
 * @author wolff
 */
public class SkeletonTurn {

    public static final String TAG = "EBTurn";

    public JSONObject data = new JSONObject();
    public int turnCounter;

    public SkeletonTurn() {
    }

    // This is the byte array we will write out to the TBMP API.
    public byte[] persist() {
        JSONObject retVal = new JSONObject();

        try {
            retVal.put("data", data);
            retVal.put("turnCounter", turnCounter);

        } catch (JSONException e) {
            Log.e("SkeletonTurn", "There was an issue writing JSON!", e);
        }

        String st = retVal.toString();

        Log.d(TAG, "==== PERSISTING\n" + st);

        return st.getBytes(Charset.forName("UTF-8"));
    }

    // Creates a new instance of SkeletonTurn.
    static public SkeletonTurn unpersist(byte[] byteArray, byte[] byteArray2) {
        JSONObject obj = new JSONObject();
        if (byteArray == null && byteArray2 == null) {
            Log.d(TAG, "Empty array---possible bug.");
            return new SkeletonTurn();
        }
        String st1 = null;
        String st2 = null;
        try {
            st1 = new String(byteArray, "UTF-8");
            obj.put("p_1", st1);
            st2 = new String(byteArray2, "UTF-8");
            obj.put("p_2", st2);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        SkeletonTurn retVal = new SkeletonTurn();

        try {
            if (obj.has("p_1")) {
                retVal.data.put("p_1", obj.get("p_1"));
            }
            if (obj.has("p_2")) {
                retVal.data.put("p_2", obj.get("p_2"));
            }
            if (obj.has("turnCounter")) {
                retVal.turnCounter = obj.getInt("turnCounter");
            }

        } catch (JSONException e) {
            Log.e("SkeletonTurn", "There was an issue parsing JSON!", e);
        }
        return retVal;
    }
}
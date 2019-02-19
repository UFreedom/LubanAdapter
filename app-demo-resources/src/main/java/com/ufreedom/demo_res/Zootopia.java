package com.ufreedom.demo_res;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: UFreedom
 * Email: sunfreedomsun@gmail.com
 * Since 2019/2/19
 */
public class Zootopia {

    private String roleName;

    private int rolePicture;

    public Zootopia(String roleName, int rolePicture) {
        this.roleName = roleName;
        this.rolePicture = rolePicture;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRolePicture() {
        return rolePicture;
    }

    public void setRolePicture(int rolePicture) {
        this.rolePicture = rolePicture;
    }

    public static List<Zootopia> getZootopiaRoleList(Context context) {
        String[] roleNameArrays = context.getResources().getStringArray(R.array.zootopia_role_name);
        List<Zootopia> zootopiaList = new ArrayList<>(roleNameArrays.length);
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.zootopia_role_picture);
        for (int i = 0; i < roleNameArrays.length; i++) {
            zootopiaList.add(new Zootopia(roleNameArrays[i], typedArray.getResourceId(i, -1)));
        }
        typedArray.recycle();
        return zootopiaList;
    }


}

package com.yy.androidsenior8;

import java.util.ArrayList;

public class DataHelper {
    static final int N = -1;
    static final int L = 0;
    static final int T = 1;
    static final int R = 2;
    static final int B = 3;
    ArrayList<Integer> data;

    DataHelper(){
        data = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            data.add(i);
        }
    }

    int getMoveDir(int index){
        int position = getPosition(index);
        int x = position % 3;
        int y = position / 3;
        if (x != 0 && data.get(position - 1) == 8){
            return L;
        }
        if (x != 2 && data.get(position + 1) == 8){
            return R;
        }
        if (y != 0 && data.get(position - 3) == 8){
            return T;
        }
        if (y != 2 && data.get(position + 3) == 8){
            return B;
        }
        return N;
    }

    void swap(int i,int r){
        Integer integer = data.get(i);
        data.set(i, data.get(r));
        data.set(r, integer);
    }

    boolean isCompleted(){
        for (int i = 0; i < 8; i++) {
            if (data.get(i) != i){
                return false;
            }
        }
        return true;
    }

    int getPosition(int index) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == index){
                return i;
            }
        }
        return -1;
    }
}

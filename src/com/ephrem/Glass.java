package com.ephrem;

import java.util.Arrays;

public class Glass {
    public static int[] twoSum(int[] nums, int target) {
        int sum = 0;
        int a = 0;
        int [] x = new int[2];

        for (int i = 0; i < nums.length; i++) {


            sum = nums[i] + nums[i++];
            if (sum == target) {
                x[i] = nums[i];
                x[i++] = nums[i++];
            }

        }
            System.out.println(sum);
            return x;

    }

    public static void main(String[] args) {
       int []y= {2,7,11,15};
        System.out.println(Arrays.toString(twoSum(y,18)));

    }


}

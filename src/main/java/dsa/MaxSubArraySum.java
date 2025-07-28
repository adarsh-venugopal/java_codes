package dsa;

public class MaxSubArraySum {

  public static void main(String[] args) {

    int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
    System.out.println(maxSubArray(nums));
  }

  public static int maxSubArray(int[] nums) {
    int maxSum = nums[0];
    int currSum = 0;

    for(int n : nums) {
      if (currSum < 0) currSum = 0;
      currSum += n;
      maxSum = Math.max(currSum, maxSum);
    }
    return maxSum;
  }

}

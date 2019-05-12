签名0d8c50145413c42b1c20a6201b992766
0D:8C:50:14:54:13:C4:2B:1C:20:A6:20:1B:99:27:66


 MD5: 0D:8C:50:14:54:13:C4:2B:1C:20:A6:20:1B:99:27:66
 SHA1: 4F:29:6B:44:BC:EE:D9:8D:8E:36:61:E7:F5:96:72:E9:F7:1B:E9:15

 在手动计算ListView高度时出现了NullPointerException错误：
 查阅资料后发现自己的ListView的item用的是RelativeLaout画的，而在Android4.4.4版本之前在手动计算View的高度而View中含有
 RelativeLayout就会出现这种错误，算是一个bug，解决办法：一是调用measure方法前判断一下版本，二是尽量不要使用RelativeLayout，
 使用LinearLayout或者FrameLayout等布局。

package android.app;

interface IActivityManager {
    int getPackageProcessState(in String packageName, in String callingPackage);
}
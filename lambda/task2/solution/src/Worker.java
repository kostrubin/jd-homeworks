public class Worker {
    private int ERROR_CODE = 33;
    private OnTaskDoneListener callback;
    private OnTaskErrorListener errorCallback;

    public Worker(OnTaskDoneListener callback, OnTaskErrorListener errorCallback) {
        this.callback = callback;
        this.errorCallback = errorCallback;
    }

    public void start() {
        for (int i = 0; i < 100; i++) {

            if (i == ERROR_CODE) {
                errorCallback.onError("Ooops! Something went wrong with " + ERROR_CODE);
                continue;
            }

            callback.onDone("Task " + i + " is done");
        }
    }

    @FunctionalInterface
    public interface OnTaskDoneListener {
        void onDone(String result);
    }

    @FunctionalInterface
    public interface OnTaskErrorListener {
        void onError(String result);
    }
}

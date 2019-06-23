package pl.raspberry.box.app;


import lombok.RequiredArgsConstructor;
import pl.raspberry.box.client.util.GameUtil;

@RequiredArgsConstructor
class ApplicationRunnable implements Runnable {

    final RaspberryBoxApplication application;

    @Override
    public void run() {
        GameUtil.sleep(1000);
        application.onApplicationStarted();
        for(int index =0; !application.isFinished(); index++) {
            application.onLoopCycle(index);
            GameUtil.sleep(100);
        }
        application.onApplicationStopped();
    }

}

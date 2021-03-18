package com.mrhi2020.ex63threadbackgroundtask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Service 를 사용하지 않고 빽그라운드 작업을 했을때의
    // 문제점을 알아보기 위한 예제
    // 문제점 : 별도 Thread 를 사용하면 Background 작업이 되는 것처럼 보이지만
    //         화면종료 후에는 Thread 를 제어할 방법이 없어짐 (참조변수가 없어서)

    MyThread myThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickStart(View view) {
        //Main Thread 는  액티비티가 화면에서 보이지 않으면 동작을 멈춤
        //별도의 Thread 를 이용하여 화면에 안보여도 동작하도록..
        if(myThread!=null) return;

        myThread= new MyThread();
        myThread.start(); //자동 run 메소드 발동

    }

    public void clickStop(View view) {
        if(myThread!=null){
            //while문을 조건을 거짓으로 하면 run메소드가 종료되도록.
            myThread.isRun= false;
            myThread=null;
        }
    }

    //5초에 한번씩 Toast를 보여주는 동작을 하는 Thread 클래스 정의
    class MyThread extends Thread{
        boolean isRun= true;
        @Override
        public void run() {
            while(isRun){
                //별도 Thread는 UI변경작업 불가
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_SHORT).show();
                    }
                });

                //5초 동안 잠시 대기
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }//while
        }//run method
    }//MyThread class

}//MainActivity class.
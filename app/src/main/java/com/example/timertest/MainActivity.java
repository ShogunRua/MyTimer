package com.example.timertest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    // 7) Создаём переменную для TextView чтобы получить к нему доступ и присвоить ему значение времени

    private TextView textViewTimer;
    // 2) для отслеживания состояния секундомера, будут использоваться две переменные:
    private int seconds = 0; // хранит кол-во прошедших секунд
    private boolean isRunning = false; // хранит признак того, работает ли секундомер в настоящий момент
    private boolean wasRunning = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 8) Присваиваем ему значение в методе onCreate
        textViewTimer = findViewById(R.id.textViewTimer);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning"); }
        wasRunning = savedInstanceState.getBoolean("wasRunning");
        // 12) Метод runTimer должен начать работать при создании данной активности, поэтому мы вызываем его в методе onCreate, сразу после создания TextView:
        runTimer();
    }
    

    @Override
    protected void onPostResume() {
        super.onPostResume();
        isRunning = wasRunning;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("wasRunning", wasRunning);
    }

    public void onClickStartTimer(View view) {
        // 3) когда пользователь кликает на кнопку старт, необходимо нашей переменной isRunning присвоить значение true:
        isRunning = true;
    }

    public void onClickStopTimer(View view) {
        // 4) при нажатии на стоп или ресет, необходимо нашей переменной снова присвоить значение false
        isRunning = false;
    }

    public void onClickResetTimer(View view) {
        isRunning = false;
        // 5) также при сбросе таймера, необходимо обнулить наш счётчик
        seconds = 0;
    }

    // 1) для обновления показания секундомера, мы будем использовать метод:
    private void runTimer() {
        // 11) В метод RunTimer добавляем новый объект Handler
        final Handler handler = new Handler();
        // Теперь нам необходимо, чтобы Handler выполнил наш метод
        // Создадим метод post который будет переопределять метод run и переместим туда весь наш код
        // На данный момент он вызывается всего один раз

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600; // кол-во часов
                int minutes = (seconds % 3600) / 60; // кол-во минут
                int secs = seconds % 60; // кол-во секунд

                // 6) теперь преобразуем эти данные в строковый формат
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                // 9) Теперь мы имеем доступ к TextView и можем установить у неё текст, равный этому времени:
                textViewTimer.setText(time);
// 10) После этого, если таймер запущен, нам нужно увеличить кол-во секунд на единицу:
                if (isRunning) {
                    seconds++;

                }
                // вызовем его ещё раз, но уже с задержкой в секуднду, в качестве параметров передаём обьект Runnable т.е. себя и пишем this. В качестве второго обьекты указываем задержку в миллисекундах.
                handler.postDelayed(this, 1000);

            }
        });


    }

}
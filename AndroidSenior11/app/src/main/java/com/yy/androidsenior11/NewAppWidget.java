package com.yy.androidsenior11;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    private static final String ACTION_PLAY = "action_play";
    private static final String ACTION_STOP = "action_stop";
    private static final int REQUEST_CODE_PLAY = 0x1001;
    private static final int REQUEST_CODE_STOP = 0x1002;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        //点击停止按钮，停止后台服务
        if (ACTION_STOP.equals(action)){
            Intent serviceIntent = new Intent(context, MyService.class);
            context.stopService(serviceIntent);
        }
        //点击了播放按钮，启动一个前台服务（8.0之后）或后台服务（8.0之前）播放
        if (ACTION_PLAY.equals(action)){
            Intent serviceIntent = new Intent(context, MyService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent);
            }else{
                context.startService(serviceIntent);
            }
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
//        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //播放图片作为按钮，绑定播放事件
        Intent intentPlay = new Intent(ACTION_PLAY);
        intentPlay.setComponent(new ComponentName(context, NewAppWidget.class));
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, REQUEST_CODE_PLAY, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.play, pendingIntentPlay);

        //停止图片作为按钮，绑定停止事件
        Intent intentStop = new Intent(ACTION_STOP);
        intentStop.setComponent(new ComponentName(context, NewAppWidget.class));
        PendingIntent pendingIntentStop = PendingIntent.getBroadcast(context, REQUEST_CODE_STOP, intentStop, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.stop, pendingIntentStop);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
    }
}


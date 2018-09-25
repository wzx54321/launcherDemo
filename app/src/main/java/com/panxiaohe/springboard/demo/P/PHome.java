package com.panxiaohe.springboard.demo.P;
// 正确的项目

import com.panxiaohe.springboard.demo.M.ModelHome;
import com.panxiaohe.springboard.demo.bean.MyAppItem;
import com.panxiaohe.springboard.demo.fragments.HomeAppFragment;
import com.panxiaohe.springboard.library.SpringboardAdapter;

import java.util.ArrayList;
import java.util.List;

import xin.framework.mvp.PresentImpl;

/**
 * 作者：xin on 2018/9/21 10:26
 * <p>
 * 邮箱：ittfxin@126.com
 */
public class PHome extends PresentImpl<HomeAppFragment> implements SpringboardAdapter.DataChangeListener<MyAppItem> {

    private ModelHome model;

    public PHome() {
        model = new ModelHome();
    }


    public List<MyAppItem> getApps() {

        List<MyAppItem> apps = model.getAppFromDB();

        if (apps == null || apps.isEmpty()) {
            apps = model.getAppFromNet();
            model.getAppBox().deleteAll();
            model.getAppBox().insert(apps);
        }

        return apps;
    }

    public SpringboardAdapter.DataChangeListener<MyAppItem> getDataChangeListener() {
        return this;
    }


    @Override
    public void onDeleteItem(ArrayList<MyAppItem> myAppItems, MyAppItem app) {// ok
        app.setDisplay(false);
        app.setSort(Integer.MAX_VALUE);// 不显示的设置为最大值了

        model.getAppBox().update(app);

        for (MyAppItem it : myAppItems) {
            it.setSort(myAppItems.indexOf(it));// 设置sort
        }

        model.getAppBox().update(myAppItems);

    }

    @Override
    public void exchangeItem(ArrayList<MyAppItem> myAppItems) {// ok
        for (MyAppItem it : myAppItems) {
            it.setSort(myAppItems.indexOf(it));// 设置sort
            it.setFolderSort(Integer.MAX_VALUE);
        }
        model.getAppBox().update(myAppItems);
    }

    @Override
    public void changeFolderName(MyAppItem app) {// ok
        model.getAppBox().update(app);
    }

    @Override
    public void exChangeSubItem(MyAppItem folder) {// ok
        if (folder.isFolder()) {
            List<MyAppItem> items = folder.getSubApps();
            for (MyAppItem item : items) {
                item.setFolderSort(items.indexOf(item));

            }
            model.getAppBox().update(items);
        }
        model.getAppBox().update(folder);
    }


    @Override
    public void mergeItem(boolean needCreateId, MyAppItem toItem, MyAppItem fromItem, ArrayList<MyAppItem> items) {//ok
        if (needCreateId) {
            // 创建一个ID
            toItem.setId(0);// 变成了文件夹
        }

        for (MyAppItem item : items) {
            item.setSort(items.indexOf(item));
            item.setFolderSort(Integer.MAX_VALUE);
            item.setDisplay(true);
            if (item.isFolder()) {
                for (MyAppItem subItem : item.getSubApps()) {
                    subItem.setFolderSort(item.getSubApps().indexOf(subItem));
                    subItem.setSort(Integer.MAX_VALUE);
                    subItem.setDisplay(true);
                }
            }
        }
        model.getAppBox().update(items);


    }

    @Override
    public void onDeleteFolderItem(MyAppItem folder, ArrayList<MyAppItem> myAppItems, MyAppItem itemBeRemove) {// ok



        itemBeRemove.setDisplay(false);
        itemBeRemove.setSort(Integer.MAX_VALUE);
        itemBeRemove.setFolderSort(Integer.MAX_VALUE);
        for (MyAppItem item : myAppItems) {
            item.setSort(myAppItems.indexOf(item));

            item.setDisplay(true);
            item.setFolderSort(Integer.MAX_VALUE);
        }
        model.getAppBox().update(itemBeRemove);
        model.getAppBox().update(myAppItems);
    }

    @Override
    public void addItem(ArrayList<MyAppItem> myAppItems) {// ok
        for (MyAppItem it : myAppItems) {
            it.setSort(myAppItems.indexOf(it));// 设置sort
            it.setDisplay(true);
        }
        model.getAppBox().update(myAppItems);
    }


    @Override
    public void addItemToFolder(boolean needCreateId, ArrayList<MyAppItem> items, MyAppItem folder, MyAppItem app) {
        // 和mergeItem 大体一个逻辑

        if (needCreateId) {
            // 创建一个ID
            folder.setId(0);// 变成了文件夹
        }


        List<MyAppItem> subApps = folder.getSubApps();
        for (MyAppItem sub : subApps) {
            sub.setFolderSort(subApps.indexOf(sub));// 重新文件排序
            sub.setDisplay(true);
            sub.setSort(Integer.MAX_VALUE);
        }


        for (MyAppItem sub : items) {// 重新排序外面
            sub.setSort(items.indexOf(sub));
            sub.setFolderSort(Integer.MAX_VALUE);// 重新文件排序
            sub.setDisplay(true);
        }

        model.getAppBox().update(items);
    }






}

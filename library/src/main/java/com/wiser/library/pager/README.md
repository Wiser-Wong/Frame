# Frame
使用ViewPager 
   ViewPager.setPageTransformer(true, new AlphaPageTransformer());
   
   xml:
                            <FrameLayout
                               android:layout_width="match_parent"
                               android:layout_height="170dp"
                               android:clipChildren="false"//重要必添加
                               android:background="@color/transparent">
   
                               <ViewPager
                                   android:id="@+id/banner"
                                   android:layout_width="match_parent"
                                   android:layout_height="140dp"
                                   android:layout_gravity="center"
                                   android:layout_marginEnd="60dp"
                                   android:clipChildren="false" />
                           </FrameLayout>
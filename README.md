<img width="450" alt="portfolio_view" src="https://github.com/Tgalshemeri/Calendario/blob/master/app/src/main/res/drawable/device-2020-12-21-101108.png">

<strong>Calendario</strong>

It is android library providing custom view of CalendarView with more options to edit the view and add and show events.

Add it in your root build.gradle:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.Tgalshemeri:Calendario:1.4'
	}
  
  Add it to the layout: 

    <com.treaf.calendario.Calendario android:layout_width="match_parent"
                                     android:layout_height="match_parent" /> 
  
  
  

<a href="https://github.com/Tgalshemeri/Calendario/wiki/Documention">Documention</a>

import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'simple_page_widgets.dart';

// void main() => runApp(MyApp());
void main() => runApp(SampleApp());

Widget _widgetForRoute(String route) {
  switch (route) {
    default:
      return MaterialApp(
        home: Scaffold(
          backgroundColor: const Color(0xFFF7C0D1), //ARGB红色
          // appBar: AppBar(
          //     title:
          //         Text("多行文本", style: TextStyle(fontWeight: FontWeight.w900))),
          drawer: Drawer(child: Center(child: Text("outlines"))),
          body: ListView.builder(
            itemCount: 2,
            itemBuilder: (context, index) {
              return Center(
                child: Text(
                  'the ${index + 1}-th: Hello from Flutter', //显示的文字
                  textDirection: TextDirection.ltr,
                  style: TextStyle(
                      fontSize: 20.0,
                      color: Colors.black,
                      fontStyle: FontStyle.normal,
                      fontWeight: FontWeight.bold),
                ),
              );
            },
          ),
        ),
      );
  }
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    if (_counter >= 2) {
      runApp(_widgetForRoute("lyn_route"));
    } else {
      setState(() {
        // This call to setState tells the Flutter framework that something has
        // changed in this State, which causes it to rerun the build method below
        // so that the display can reflect the updated values. If we changed
        // _counter without calling setState(), then the build method would not be
        // called again, and so nothing would appear to happen.
        _counter++;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          // Column is also a layout widget. It takes a list of children and
          // arranges them vertically. By default, it sizes itself to fit its
          // children horizontally, and tries to be as tall as its parent.
          //
          // Invoke "debug painting" (press "p" in the console, choose the
          // "Toggle Debug Paint" action from the Flutter Inspector in Android
          // Studio, or the "Toggle Debug Paint" command in Visual Studio Code)
          // to see the wireframe for each widget.
          //
          // Column has various properties to control how it sizes itself and
          // how it positions its children. Here we use mainAxisAlignment to
          // center the children vertically; the main axis here is the vertical
          // axis because Columns are vertical (the cross axis would be
          // horizontal).
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}

class SampleApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<SampleApp> {
  @override
  void initState() {
    super.initState();

    FlutterBoost.singleton.registerPageBuilders(<String, PageBuilder>{
      'embeded': (String pageName, Map<String, dynamic> params, String _) =>
          EmbeddedFirstRouteWidget(),
      'first': (String pageName, Map<String, dynamic> params, String _) => FirstRouteWidget(),
      'firstFirst': (String pageName, Map<String, dynamic> params, String _) =>
          FirstFirstRouteWidget(),
      'second': (String pageName, Map<String, dynamic> params, String _) => SecondRouteWidget(),
      'secondStateful': (String pageName, Map<String, dynamic> params, String _) =>
          SecondStatefulRouteWidget(),
      'tab': (String pageName, Map<String, dynamic> params, String _) => TabRouteWidget(),
      'platformView': (String pageName, Map<String, dynamic> params, String _) =>
          PlatformRouteWidget(),
      'flutterFragment': (String pageName, Map<String, dynamic> params, String _) =>
          FragmentRouteWidget(params),

      ///可以在native层通过 getContainerParams 来传递参数
      'flutterPage': (String pageName, Map<String, dynamic> params, String _) {
        print('flutterPage params:$params');

        return FlutterRouteWidget(params: params);
      },
    });
    FlutterBoost.singleton.addBoostNavigatorObserver(TestBoostNavigatorObserver());
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Flutter Boost example',
        builder: FlutterBoost.init(postPush: _onRoutePushed),
        home: Container(color: Colors.white));
  }

  void _onRoutePushed(
      String pageName,
      String uniqueId,
      Map<String, dynamic> params,
      Route<dynamic> route,
      Future<dynamic> _,
      ) {}
}

class TestBoostNavigatorObserver extends NavigatorObserver {
  @override
  void didPush(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didPush');
  }

  @override
  void didPop(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didPop');
  }

  @override
  void didRemove(Route<dynamic> route, Route<dynamic> previousRoute) {
    print('flutterboost#didRemove');
  }

  @override
  void didReplace({Route<dynamic> newRoute, Route<dynamic> oldRoute}) {
    print('flutterboost#didReplace');
  }
}

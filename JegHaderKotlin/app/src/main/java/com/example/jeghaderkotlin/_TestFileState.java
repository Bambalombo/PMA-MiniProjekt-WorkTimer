package com.example.jeghaderkotlin;

class _TestFileState extends State<TestFile> {
    _TestFileState() {
        showModalBottomSheet(
                context: context,
                backgroundColor: Colors.white,
                shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.only(
                topLeft: Radius.circular(15.0), topRight: Radius.circular(15.0)),
        ),
        builder: (context) {
        return Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                mainAxisSize: MainAxisSize.max,
                children: <Widget>[
        ListTile(
                leading: Icon(Icons.email),
                title: Text('Send email'),
                onTap: () {
            print('Send email');
        },
              ),
        ListTile(
                leading: Icon(Icons.phone),
                title: Text('Call phone'),
                onTap: () {
            print('Call phone');
        },
              ),
            ],
          );
        });
    }

    @override
    Widget build(BuildContext context) {
        return SafeArea(
                child: Scaffold(
                appBar: AppBar(
                title: Center(child: Text('Testing Modal Sheet')),
        ),
        body: Center(
                child: InkWell(
                onTap: () {
            modalSheet();
        },
        child: Container(
                color: Colors.indigo,
                height: 40,
                width: 100,
                child: Center(
                child: Text(
                'Click Me',
                style: TextStyle(color: Colors.white),
                  ),
                )),
          ),
        ),
      ),
    );
    }
}
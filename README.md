# CoPainter
HKU/COMP2396 Java Programming/assignment 6 collaboration repository

### Authors:
Name | UID
-------------|-----------
CHEN, Zhihan | 3035142261
WANG, Zixu   | 3035140067

## Welcome to CoPainter!
CoPainter is a online painting software which allows you to
draw things with your friends over the Internet.

It supports multi-client connection, full-color and size brushes, and file saving/loading.
It runs on different platforms as it is written in pure Java.
##### Connection:
If you start the CoPainter as a server/host,
you can click 'Help -> About' in the menu bar
to get the connection details to tell your friends
to connect to you. You may also check the number
of connections there.

To start the CoPainter as a client,
you need to type in the IP address as well as
the port of the server in the start-up connection prompt.
Once connected, the main frame will appear.
You can access 'Help -> About' in the menu bar
to check current connection status.
			
##### Painting:
In the main frame, you can draw lines by
easily clicking or dragging your mouse.
The cursor is the preview of your current brush stroke.

You can quickly change the color of your brush
to one of the preset colors at bottom left
by simply clicking it. You can also customize the color
by clicking the 'Custom' button and pick your favorite color
in the color chooser.

You can also quickly change the size of the brush
by clicking the preset sizes at bottom right.
You may also directly input the size you want
in the text field and click 'Set' or press Enter.
The '+1'/'-1' buttons can be used to quickly adjust
the size. You can press the buttons and hold your mouse
to continuously increase or decrease the size.
			
##### Control:
If you want to save your masterpiece, simply click
'Control -> Save' in the menu bar to save the .pb
painting board file to your computer.
The host can load .pb files to the painting board
and to all the clients via 'Control -> Load' in the menu bar.
The host can also clear the whole board
via 'Control -> Clear' in the menu bar.
			
##### That's all! Hope you enjoy using the CoPainter.

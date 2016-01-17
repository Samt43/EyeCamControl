# EyeCamControl
Java Library to control G-Eye 300,500,700 and Xiaomi Yi

How the code works :
- This code connect to ports 7878 and 8787 using TCP
- It sends order and receive responses on the port 7878 in JSON
- It use 8787 to download files from the camera files (in future to upload files too)

This code has been tested with Geye 500 camera but should work with model 300 and 700 too.
It should also work on camera based on the A7LS chipset. (Xiaomi Yi...)

A small program in console shows how to use the controler.

You can currently :
- View/Modify an option on the camera
- Take a photo (video is coming)
- Download a file from yor camera

Your computer just need to be on the same network as the camera and the camera should use the adress 192.168.42.1 (default one)

# Lesson 5. Deploy your bot

I think, when you are reading this, you have created your own bot with the help from my book. So now, its time to run it not on your home computer ~~with Intel Pentium II~~, but on professional server hardware. I will show how to deploy your bot on [DigitalOcean hosting](https://m.do.co/c/1a3a7fad419f).

## Creating droplet
Firstly, you need to create account on DigitalOcean. Open [this link](https://m.do.co/c/1a3a7fad419f), enter your email and password and click "Create an account"

![Register at DO](https://pp.vk.me/c638720/v638720821/20613/MSBDKWZBGD4.jpg "Register")

Then, follow register insctructions. Once you are in your control panel, create a new droplet. 

Select OS. I recommend using Ubuntu 16.04.01 x64. Then choose preffered plan. For Java bot, you can select 512-1GB RAM (its enough for start). Select datacenter's region (I recommend to choose nearest city), scroll down and click "Create". Check your email inbox for letter like this:

![Droplet email](https://pp.vk.me/c638720/v638720821/20623/_v4iI97Y-WQ.jpg "Droplet settings email")

## Connecting via SSH

You will need next software for that:

* [PuTTY SSH Client](http://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html)
* [FileZilla FTP Client](https://filezilla-project.org/)

When you install it, open PyTTY and write server IP and port (default 22).

![PyTTY login](https://pp.vk.me/c638720/v638720821/2062a/9fh0hk51pk8.jpg "PyTTY login")

And click "Open". You will see something like:

![PyTTY Security Alert](https://pp.vk.me/c638720/v638720821/20632/3uszaTvQs2Y.jpg "PuTTY Security Alert")

Click "Yes". Then login as "root" user and with password that you have recieved via email. Now we need to install Java on your server. Type following:

```bash
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install default-jre
sudo apt-get install default-jdk
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
```

{% asciinema %}https://asciinema.org/a/ctsijyfcmsnhruzwr87sf1ye6{% endasciinema %}

Type `java -version` to check installation. You will see something like that:

![Java Version](https://pp.vk.me/c638720/v638720821/2063b/-I-QQPjSLcw.jpg "Java Version")

## Creating and uploading JAR

Now, open `IntelliJ Idea` and go to File>Project Structure>Artifacts> + (Add artifact)>JAR>From modules with dependencies>Select main class (`Main.java`)>OK>Apply.
Then Build>Build Artifacts>Build. Then in the `out/artifacts/` folder you will see your JAR.

### Uploading to the server

Open FileZilla and enter IP address, username, password and port (default 22) and click "Connect". Create new folder on your server (right window), open it and upload you JAR by dragging it from left window (local computer) to right window (server). Open PuTTY and type:

```bash
screen
```

Press `ENTER` and then go to your folder:

```bash
cd FOLDER_NAME
```

And just run your JAR:

```bash
java -jar FILE.jar
```

Well done! You are now hosting your bot at DigitalOcean. To exit terminal, press `CTRL + A + D` and then type `exit`. To return, connect via SSH, type `screen -r` and you will be returned to your screen session. To stop the bot: `CTRL + C`. 

Well, that's all for now. See you soon!

# Nanoleaf Aurora Java API
A java wrapper and JSON parser for the Nanoleaf Aurora RESTful API that fully supports all features of the official API, but adds additional high-level helper methods and classes to make your life easier. The API is designed to be simpler to use than the official API by abstracting the use of JSON data.

## [Documentation](https://htmlpreview.github.io/?https://github.com/rowak/nanoleaf-aurora/blob/master/doc/index.html)

## Installation
### Maven
Simply add the following dependency to your ```pom.xml``` file in your project.
```xml
<dependency>
  <groupId>io.github.rowak</groupId>
  <artifactId>nanoleaf-aurora</artifactId>
  <version>1.3.1</version>
</dependency>
```
### Manual
You can also download the [compilled jar](http://central.maven.org/maven2/io/github/rowak/nanoleaf-aurora/) and import it into your project.

## Connecting to the Aurora
First, search for all of the Aurora devices connected to your local network, then select one from the returned list. Make sure to store the host name and port.
```Java
int timeout = 5000;
List<InetSocketAddress> auroras = Setup.quickFindAuroras()
String host = auroras.get(0).getHostName();
int port = auroras.get(0).getPort();
```
Next define the API level (currently "v1").
```Java
String apiLevel = "v1";
```
Then you can create an access token. (Note: For security purposes, you must physically hold down the power button on your Aurora controller for 5-7 seconds until the LED starts flashing before running the following code). Make sure to write down your access token for future use, however you can create as many as you like.
```Java
String accessToken = Setup.createAccessToken(host, port, apiLevel);
```
Finally you can create an ```Aurora``` object.
```Java
Aurora aurora = new Aurora(host, port, apiLevel, accessToken);
```

Note: You can also directly create a new ```Aurora``` object if you already have an access token and you know the host, port, and apiLevel of your Aurora.

## Controlling the Aurora
Once you have created an ```Aurora``` object, you can start using its methods.
### State
Contains basic methods used for getting and setting Aurora state information (brightness, color, etc). Below are a few examples.
#### On/Off
```Java
boolean isOn = aurora.state().getOn();  // returns true if the Arora is on and false if it is off
aurora.state().setOn(true/false);       // sets the on state of the Aurora
aurora.state().toggleOn();              // toggles the state of the Aurora (on -> off, off -> on)
```
#### Brightness
```Java
int brightness = aurora.state().getBrightness();  // returns the brightness of the Aurora
aurora.state().setBrightness(0-100);              // sets the brightness of the Aurora (max = 100, min = 0)
```
#### Hue
```Java
int hue = aurora.state().getHue();  // returns the hue of the aurora (solid effects only)
aurora.state().setHue(0-360);       // sets the hue of the aurora (max = 360, min = 0)
```
#### Saturation
```Java
int saturation = aurora.state().getSaturation();  // returns the saturation of the Aurora (solid effects only)
aurora.state().setSaturation(0-100);              // sets the saturation of the Aurora (max = 100, min = 0)
```
#### Color Temperature
```Java
int colorTemp = aurora.state().getColorTemperature()  // returns the color temperature in Kelvins of the Aurora (color temperature effects only)
aurora.state().setColorTemperature(1200-6500);        // sets the color temperature of the Aurora (color temperature effects only)
```

### Effects
The API includes various effect methods for adding, removing, renaming, previewing, and getting effects from the Aurora. Below are a few examples, but refer to the [project documentation](https://htmlpreview.github.io/?https://github.com/rowak/nanoleaf-aurora/blob/master/doc/index.html) for more information.
```Java
String currentEffect = aurora.effects().getCurrentEffectName();  // returns the name of the current effect
aurora.effects().setEffect(effectName);                          // sets the current effect to an existing effect
aurora.effects().addEffect(effect);                              // adds a new Effect object to the Aurora
aurora.effects().deleteEffect(effect);                           // removes an Effect object from the Aurora
aurora.effects().renameEffect(effectName, newName);              // renames an existing effect
aurora.effects().previewEffect(effect);                          // displays an effect on the Aurora, but does not install it
```

### Panel Layout
The panel layout methods are mostly getters used to get various information about the arrangement of the Aurora panels. Below are a few examples of these methods.
```Java
int numPanels = aurora.panelLayout().getNumPanels(true/false);  // returns the number of connected panels. Note: The Rhythm module counts as a panel by default. Use ```includeRhythm``` to choose whether to include this or not
int sideLength = aurora.panelLayout().getSideLength();          // returns the side length of each panel
Panel[] panels = aurora.panelLayout().getPanels();              // returns an array of type Panel containing each connected Aurora panel
```

### Rhythm
The Rhythm aspect of the java API mostly contains getters for information such as mode, connected/not connected, active/not active, aux available, etc. Below are a few examples.
```Java
boolean connected = aurora.rhythm().getConnected();        // whether or not the Rhythm is connected to the Aurora
boolean active = aurora.rhythm().getActive();              // whether or not the Rhythm's microphone is currently active (blue led is on)
boolean auxAvailable = aurora.rhythm().getAuxAvailable();  // whether of not the aux (3.5mm) input is available
```

### External Streaming
External streaming is an advanced feature that allows for continuous updating of the Aurora panels by sending UDP packets. External streaming can be enabled using the method ```Aurora.ExternalStreaming.enable()```. Static animation data can be sent to the Aurora using the method ```Aurora.ExternalStreaming.sendAnimData()```. Effects (with animation data) can also be sent using the method ```Aurora.ExternalStreaming.sendStaticEffect()```. Individual panels can be updated using the method ```Aurora.ExternalStreaming.setPanel()```. *Note that these methods do not return anything. If the data sent is invalid, the server will not send any kind of response*.

## The Effect Class
The Effect class is a helper class for parsing raw effect json data received from the Aurora into a **local** object. This allows for easier reading from and writing to effects, and helps make creating new effects much easier. The Aurora class implements these methods where necessary by default so you don't have call them yourself.
Note: The instance variables in Effect objects are not all used by certain effect types. Attempting to get these variables will either result in -1 (int/double) or ```null``` (String/Color[]). Use the [official API documentation](http://forum.nanoleaf.me/docs/openapi#_e5qyi8m8u68) as a reference when working with Effect objects.
### Example
The code below requests an effect named "My Effect" from the Aurora, sets the effect's delay time to 10, then uploads the changes back to the Aurora. Note that not all effect properties are supported by all effects.
```Java
Effect effect = aurora.effects().getEffect("My Effect");  // creates a new Effect object by automatically parsing the json data
effect.setDelayTime(10);                                  // sets the effect delay time. Note: This change does not affect the physical Aurora display yet, only the local Effect object
aurora.effects().addEffect(effect);                       // uploads the modified effect to the Aurora and the changes are applied
```
### Warning
Changing json data in effects that you upload to your Aurora can cause the Nanoleaf app to crash if you attempt to change properties that shouldn't be changed for certain effects. If your Nanoleaf app suddenly starts crashing, use the ```Aurora.Effect.getEffectsList()``` method to list all of the effects, then use the ```Aurora.Effect.deleteEffect()``` method to remove any problem effects. I constantly came across this problem while messing around with the api. Refer to the [official API documentation](http://forum.nanoleaf.me/docs/openapi#_e5qyi8m8u68) for more information about effects and their properties.

## Effect Builders
Effect builders are small helper classes that assist in creating effects programatically. These classes implement the ```EffectBuilder``` interface except for the ```CustomEffectBuilder``` and the ```StaticEffectBuilder```.
### The CustomEffectBuilder
The ```CustomEffectBuilder``` class is a more advanced helper class that assists in the creation of ```custom```-type effects. Animation frames are added to the builder object using the ```CustomEffectBuilder.addFrame()``` method. This method adds a frame for only **one** panel at a time. Once all of the frames have been defined, an ```Effect``` object can be built from the animation object using the ```CustomEffectBuilder.build()``` method.
#### Example
```Java
Panel[] panels = aurora.panelLayout().getPanels();              // creates a new array of type Panel, containing all of the connected Panel data
CustomEffectBuilder builder = new CustomEffectBuilder(aurora);  // creates an instance of the effect builder object
for (Panel panel : panels)
{
  builder.addFrame(panel, new Frame(255, 0, 255, 0, 20));       // add a new frame to the animation (for each panel). Frame takes 5 arguments: red, green, blue, white, and transitionTime
  builder.addFrame(panel, new Frame(0, 255, 255, 0, 20));       // add a new frame to the animation (for each panel). Frame takes 5 arguments: red, green, blue, white, and transitionTime
}
Effect effect = builder.build("My Animation", true);            // builds the animation data and saves it to a new custom-type effect
aurora.effects().addEffect(effect);                             // uploads the new effect to the Aurora
```

## StatusCodeExceptions
A StatusCodeException is thrown whenever the response code from a request to the Aurora is an error (400, 401, 403, 404, 422, 500).
### 400 - Bad Request
Indicates that the request body itself was malformed (not the reqeust body content). *This exception is usually handelled by the API and is not usually accessable by the user.*
### 401 - Unauthorized
Indicates that the resource that was requested requires a valid access token. If this exception is thrown, your access token is likely invalid.
### 403 - Forbidden
Indicates that the user tried to create an access token without first physically holding down the power button on the Aurora for 5-7 seconds. This exception can only be thrown by the ```createAccessToken()``` method.
### 404 - Resource Not Found
Indicates that the resource that was requested from the Aurora does not exist. An example of when this may occur is when calling ```Aurora.getEffect(String effectName)```. If the effect ```effectName``` does not exist on the Aurora, then this exception will be thrown. 
### 422 - Unprocessable Entity
Indicates that the body content of a request is invalid. If this exception is thrown, one or more arguments may have been invalid when an API method was called.
### 500 - Internal Server Error
I'm not sure what exactly causes this error, but something goes wrong inside the Aurora (this exception can only be thrown by the ``Setup.destroyAccessToken()`` method).

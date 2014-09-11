mimicry
=================
Mimicry is a tiny Android library -- actually just one class -- to format items in a `ListAdapter` to display as a grid. Why not just use a `GridView`? Because a `MimicryAdapter` can be used with other `ListAdapter`s!

![Screen shot of the mimicry-sample app](http://2312.nl/download/mimicry-sample_framed.png)

Usage
------------
You can add the mimicry project directly as Android Library project or, if you prefer, just copy the sole `nl.nl2312.mimicry.library.MimicryAdapter` class to your project.
```java
ListAdapter wrappedAdapter = new ArrayAdapter<String>(this, ..., ...);
ListAdapter mimicryAdapter = new MimicryAdapter(this, 3, wrappedAdapter);
listView.setAdapter(mimicryAdapter);
```
Since every row represented by the `MimicryAdapter` actually contains multiple items, the normal `OnItemClickListener` you use with a `ListView` is not sufficient. Instead, attach a `OnMimicryItemClickedListener` to catch item click events.
```java
mimicryAdapter.setOnMimicryItemClicked(new OnMimicryItemClickedListener() {
    @Override
    public void onItemClicked(Object item) {
        // item is the object that teh wrapped adapter returned on getItem(Object)
    }
});
```

View recycling is supported as per the 0.2 release, but note that this requires the wrapped adapter to only use one view type (which is probably what you want anyway). A RuntimeException is thrown if multiple view types are explicitly reported by the wrapped adapter.

Contributing
------------
Feel free to improve the code and send me pull requests! Perhaps the internal layout code could be improved, as the layout params of the contained views is overriden at the moment.

License
------------
Designed and developed by [Eric Kok](mailto:eric@2312.nl) of [2312 development](http://2312.nl).

    Copyright 2014 Eric Kok
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

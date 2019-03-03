

<p align="center">
  <img  with="750"  height="250" src="https://github.com/UFreedom/LubanAdapter/blob/master/art/banner.png">
</p>

<h3 align="center">Luban Adapter</h1>

<div align="center">

Make it more easy to work with RecyclerView.


</div>

#### 📎 Lastest version

module|LubanAdapter-api|LubanAdapter-compiler
---|---|---|
version|[![Download](https://api.bintray.com/packages/ufreedom/maven/lubanadapter-api/images/download.svg) ](https://bintray.com/ufreedom/maven/lubanadapter-api/_latestVersion)|[ ![Download](https://api.bintray.com/packages/ufreedom/maven/lubanadapter-compiler/images/download.svg) ](https://bintray.com/ufreedom/maven/lubanadapter-compiler/_latestVersion)




### 👉 Features


### 🖥 Usage

##### Step 1

Add dependencies in build.gradle.

```groovy
    dependencies {
        compile 'com.ufreedom.kit:lubanadapter-api:1.2.0'
        annotationProcessor 'com.ufreedom.kit:lubanadapter-compiler:1.2.0.1'
    }

```

##### Step 2

Create your model

```java
    public class Animals {
    }
```

And then create your ViewHolder

```java
    public class ImageHolder extends LubanViewHolder<Animals> {

    public ImageHolder(View itemView) {
        super(itemView);
        //Hear to find views
    }

    @Override
    public void onBindViewHolder(Animals animalsModel, int position) {
        //Hear to bind view with you model data
    }
}
```

##### Step 3
Use LubanAdapter to bind your layout,model,and ViewHolder.
Thear have two easy way to do this:

- Use LubanAdapterHelper

```java
       LubanAdapter<Animals> lubanAdapter = LubanAdapterHelper.create(context)
                .register(Animals.class, R.layout.view_item_animals,AnimalsHolder.class)
                .apply();
    lubanAdapter.setAdataList(AnimalsList);
    recyclerView.setAdapter(lubanAdapter);
```

- Use @BindType or @BindTypes annotations

Use anotations is much more easy

```java
 @BindType(layout = R.layout.view_item_animals, model = Animals.class, holder = AnimalsHolder.class)
public class AnimalsAdapter extends LubanAdapter<Animals> {
    public DemoAdapter(Context context) {
        super(context);
    }
}
recyclerView.setAdapter(new AnimalsAdapter(content));
```

License
--------

    Copyright 2019 UFreedom

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

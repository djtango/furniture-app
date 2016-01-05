var World = {
	loaded: false,
	rotating: false,

	init: function initFn() {
		console.log('init: i have been called');

		this.createModelAtLocation();
	},

	createModelAtLocation: function createModelAtLocationFn() {
		console.log('createModelAtLocationFn: i have been called');
		/*
			First a location where the model should be displayed will be defined. This location will be relativ to the user.
		*/
		var location = new AR.RelativeLocation(null, 5, 0, 2);

		/*
			Next the model object is loaded.
		*/
		var modelEarth = new AR.Model("earth.wt3", {
			onLoaded: this.worldLoaded,
			scale: {
				x: 1,
				y: 1,
				z: 1
			}
		});

		console.log('modelEarth: ' + modelEarth);

        var indicatorImage = new AR.ImageResource("indi.png");

        console.log('indicatorImage: ' + indicatorImage);


        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });

		/*
			Putting it all together the location and 3D model is added to an AR.GeoObject.
		*/
		var obj = new AR.GeoObject(location, {
            drawables: {
               cam: [modelEarth],
               indicator: [indicatorDrawable]
            }
        });
	},

	worldLoaded: function worldLoadedFn() {
		World.loaded = true;
		var e = document.getElementById('loadingMessage');
		e.parentElement.removeChild(e);
		console.log('worldLoaded: I have been called');
	}
};

World.init();

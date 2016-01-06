var World = {
	loaded: false,
	rotating: false,
	lastTouch: {
		x: 0,
		y: 0
	},
	interactionContainer: 'gestureContainer',

	init: function initFn() {
		this.createModelAtLocation();
	},

	createModelAtLocation: function createModelAtLocationFn() {
		var location = new AR.RelativeLocation(null, 5, 0, 2);
		World.modelEarth = new AR.Model(targetFile, {
			onLoaded: this.worldLoaded,
			scale: {
				x: 1,
				y: 1,
				z: 1
			},
			translate: {
				x: 0.0,
				y: 0.05,
				z: 0.0
			}
		});

		console.log('modelEarth: ' + this.modelEarth);

        var indicatorImage = new AR.ImageResource("indi.png");
        var imgRotate = new AR.ImageResource("rotateButton.png");

        console.log('indicatorImage: ' + indicatorImage);


        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });






//		var buttonRotate = new AR.ImageDrawable(imgRotate, 0.2, {
//			offsetX: 0.35,
//			offsetY: 0.45,
//			//zOrder: 100,
//			verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP

			// onClick: this.toggleAnimateModel // GEORGE NOTE buttonRotate (along with buttonSnap below) gets passed to the drawables object in this.trackable below, which I believe is the creation of the whole thing - the tracker and the drawables (objects, 3D or otherwise), attached to it
//		});

		/*
			Putting it all together the location and 3D model is added to an AR.GeoObject.
		*/
		var obj = new AR.GeoObject(location, {
            drawables: {
               cam: [this.modelEarth],
               indicator: [indicatorDrawable]
            }
        });

        World.addInteractionEventListener();
	},

	worldLoaded: function worldLoadedFn() {
		World.loaded = true;
		var e = document.getElementById('loadingMessage');
		e.parentElement.removeChild(e);
		console.log('worldLoaded: I have been called');
	},

	handleTouchStart: function handleTouchStartFn(event) {
		console.log('handleTouchStart has been called');
		World.swipeAllowed = true;

		/* Once a new touch cycle starts, keep a save it's originating location */
		World.lastTouch.x = event.touches[0].clientX;
		World.lastTouch.y = event.touches[0].clientY;

		event.preventDefault();
	},

	handleTouchMove: function handleTouchMoveFn(event) {

		console.log('handleTouchMove has been called!')
		if (World.swipeAllowed){
			/* Define some local variables to keep track of the new touch location and the movement between the last event and the current one */
			var touch = {
				x: event.touches[0].clientX,
				y: event.touches[0].clientY
			};
			var movement = {
				x: 0,
				y: 0
			};


			/* Calculate the touch movement between this event and the last one */
			movement.x = (World.lastTouch.x - touch.x) * -1;
			movement.y = (World.lastTouch.y - touch.y) * -1;


			/* Rotate the car model accordingly to the calculated movement values. Note: we're slowing the movement down so that the touch action feels better */
			World.modelEarth.translate.x += (movement.x * 0.1);
			World.modelEarth.translate.z += (movement.y * 0.1);


			/* Keep track of the current touch location. We need them in the next move cycle */
			World.lastTouch.x = touch.x;
			World.lastTouch.y = touch.y;
		}

		event.preventDefault();
    },

	addInteractionEventListener: function addInteractionEventListenerFn() {
		console.log('addInteractionEventListener called')
		document.getElementById(World.interactionContainer).addEventListener('touchstart', World.handleTouchStart, false);
		document.getElementById(World.interactionContainer).addEventListener('touchmove', World.handleTouchMove, false);

//		document.getElementById(World.interactionContainer).addEventListener('gesturestart', World.handleGestureStart, false);
//		document.getElementById(World.interactionContainer).addEventListener('gesturechange', World.handleGestureChange, false);
//		document.getElementById(World.interactionContainer).addEventListener('gestureend', World.handleGestureEnd, false);
	}

};

World.init();

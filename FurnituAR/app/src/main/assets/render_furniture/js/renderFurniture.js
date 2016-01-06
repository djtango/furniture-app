console.log('selection: ' + selectionData.selection);
console.log('y-bearing: ' + selectionData.bearingN);
console.log('x-bearing: ' + selectionData.bearingE);


var World = {
	loaded: false,
	rotating: false,
	lastTouch: {
		x: 0,
		y: 0
	},
	rotateOrTranslate: 'translate',
	interactionContainer: 'gestureContainer',

	init: function initFn() {
		this.createModelAtLocation();
		console.log('init: i have been called');
	},

	createModelAtLocation: function createModelAtLocationFn() {
		console.log('createModelAtLocationFn: i have been called');

		var location = new AR.RelativeLocation(null, selectionData.bearingN * 5, selectionData.bearingE * 5, 1);

		World.model3DObj = new AR.Model(selectionData.selection + '.wt3', {
			onLoaded: this.worldLoaded,
			scale: {
				x: 0.7,
				y: 0.7,
				z: 0.7
			},
			translate: {
				x: 0.0,
				y: 0.05,
				z: 0.0
			}
		});

		console.log('model3DObj: ' + this.modelEarth);

        var indicatorImage = new AR.ImageResource("indi.png");
        var imgRotate = new AR.ImageResource("rotateButton.png");

        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });

		var obj = new AR.GeoObject(location, {
            drawables: {
               cam: [this.model3DObj],
               indicator: [indicatorDrawable]
            }
        });

        World.addInteractionEventListener();
	},

	worldLoaded: function worldLoadedFn() {
		World.loaded = true;
		var e = document.getElementById('loadingMessage');
		e.parentElement.removeChild(e);
	},

	handleTouchStart: function handleTouchStartFn(event) {
		World.swipeAllowed = true;

		World.lastTouch.x = event.touches[0].clientX;
		World.lastTouch.y = event.touches[0].clientY;

		event.preventDefault();
	},

	handleTouchMove: function handleTouchMoveFn(event) {

		console.log('handleTouchMove has been called!')
		if (World.swipeAllowed){
			var touch = {
				x: event.touches[0].clientX,
				y: event.touches[0].clientY
			};
			var movement = {
				x: 0,
				y: 0
			};

			movement.x = (World.lastTouch.x - touch.x) * -1;
			movement.y = (World.lastTouch.y - touch.y) * -1;

			if(World.rotateOrTranslate === 'translate'){

				World.model3DObj.translate.x += (movement.x * 0.1);
				World.model3DObj.translate.z += (movement.y * 0.1);
			} else{
				World.model3DObj.rotate.heading += (movement.x * 0.3);
				World.model3DObj.rotate.tilt += (movement.y * 0.3);
			}

			World.lastTouch.x = touch.x;
			World.lastTouch.y = touch.y;
		}

		event.preventDefault();
    },

	addInteractionEventListener: function addInteractionEventListenerFn() {
		console.log('addInteractionEventListener called')
		document.getElementById(World.interactionContainer).addEventListener('touchstart', World.handleTouchStart, false);
		document.getElementById(World.interactionContainer).addEventListener('touchmove', World.handleTouchMove, false);
		document.getElementById("rotate_translate_anchor").addEventListener("click", function() {
			if(World.rotateOrTranslate === 'translate'){
				World.rotateOrTranslate = 'rotate'
				console.log('rotateOrTranslate: ' + World.rotateOrTranslate)
			} else{
				World.rotateOrTranslate = 'translate'
				console.log('rotateOrTranslate: ' + World.rotateOrTranslate)
			}
		});
	}

};

World.init();

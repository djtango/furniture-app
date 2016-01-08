console.log('selection: ' + selectionData.selection);
console.log('y-bearing: ' + selectionData.bearingN);
console.log('x-bearing: ' + selectionData.bearingE);


var World = {
	loaded: false,
	rotating: false,
	jsonData: undefined,
	lastTouch: {
		x: 0,
		y: 0
	},
	rotateOrTranslate: 'translate',
	interactionContainer: 'gestureContainer',
	previousOrientation: undefined,
	helpMessageShows: true,
	externalBearing: 0,

	init: function initFn(filename) {
		this.createModelAtLocation(filename);
	},

	createModelAtLocation: function createModelAtLocationFn(filename) {

		var location = new AR.RelativeLocation(null, selectionData.bearingN * 50, selectionData.bearingE * 50, 0);
		World.model3DObj = new AR.Model(filename + '.wt3', {
			onLoaded: this.worldLoaded,
			scale: {
				x: 0.2,
				y: 0.2,
				z: 0.2
			},
			translate: {
				x: 0.0,
				y: 1, //originally 0.05
				z: 0.0
			}
		});

        var indicatorImage = new AR.ImageResource("indi.png");
        var imgRotate = new AR.ImageResource("rotateButton.png");

        var indicatorDrawable = new AR.ImageDrawable(indicatorImage, 0.1, {
            verticalAnchor: AR.CONST.VERTICAL_ANCHOR.TOP
        });

		var obj = new AR.GeoObject(location, {
            drawables: {
               cam: [this.model3DObj],
//               indicator: [indicatorDrawable]
            }
        });

        World.addInteractionEventListener();
	},

	worldLoaded: function worldLoadedFn() {
		World.loaded = true;
		var e = document.getElementById('loadingMessage');
		e.parentElement.removeChild(e);
	},

	toggleHelpMessage: function() {
		console.log('toggleHelpMessage called');
		var helpMessageElement = document.getElementById('help_panel');

		if(World.helpMessageShows){
			helpMessageElement.style.display = 'none';
		}
		else{
			helpMessageElement.style.display = 'block';
		}

		World.helpMessageShows = !(World.helpMessageShows);
		console.log('helpMessageShows: ' + World.helpMessageShows);
		World.addInteractionEventListener();
	},

	handleTouchStart: function handleTouchStartFn(event) {
		World.swipeAllowed = true;

		World.lastTouch.x = event.touches[0].clientX;
		World.lastTouch.y = event.touches[0].clientY;

		event.preventDefault();
	},

	handleTouchMove: function handleTouchMoveFn(event) {

//		console.log('handleTouchMove has been called!')
		if (World.swipeAllowed){
			var touch = {
				x: event.touches[0].clientX,
				y: event.touches[0].clientY
			};
			var movement = {
				x: 0,
				y: 0
			};
			var realignedMovement = World.calculateMovement(touch);

			movement.x = realignedMovement.x // World.calculateXMovement(touch); // (World.lastTouch.x - touch.x) * -1;
			movement.y = realignedMovement.y // World.calculateYMovement(touch); // (World.lastTouch.y - touch.y) * -1;

			if(World.rotateOrTranslate === 'translate'){

				World.model3DObj.translate.x += (movement.x * 0.25);
//				console.log('y changing by ' + (movement.y * 0.25))
				World.model3DObj.translate.z += (movement.y * 0.25);

			} else{

				World.model3DObj.rotate.heading += (movement.x * 0.3);
				World.model3DObj.rotate.tilt += (movement.y * 0.3);
			}

			World.lastTouch.x = touch.x;
			World.lastTouch.y = touch.y;
		}

		event.preventDefault();
    },

    raiseButton: function() {

    	World.model3DObj.translate.y += 1.2;
    	console.log('translate Y: ' + World.model3DObj.translate.y)

    },

	lowerButton: function() {

		World.model3DObj.translate.y -= 1.2;
		console.log('translate Y: ' + World.model3DObj.translate.y)

	},

    rotateTranslateToggle: function() {

		if(World.rotateOrTranslate === 'translate'){
			World.rotateOrTranslate = 'rotate'
			console.log('rotateOrTranslate: ' + World.rotateOrTranslate)
		} else{
			World.rotateOrTranslate = 'translate'
			console.log('rotateOrTranslate: ' + World.rotateOrTranslate)
		}
	},

	calculateAxes: function() {
		var landscape = 90;

		if(window.orientation === landscape) {
            World.isFlipXOn = true;
            World.isFlipYOn = true;
		} else {
			World.isFlipXOn = false;
			World.isFlipYOn = false;
		}
	},

	calculateMovement: function(touch) {
		var realignedMovement = { 'x': 0, 'y': 0 };
		var diffX = World.lastTouch.x - touch.x;
		var diffY = World.lastTouch.y - touch.y;
//		var bearing = World.jsonData.bearing;
		var bearing = World.externalBearing;
		console.log("calculateMovement.bearing: " + bearing);
		realignedMovement.x = diffX * Math.cos(bearing) + diffY * Math.sin(bearing);
		realignedMovement.y = diffY * Math.cos(bearing) + diffX * Math.sin(bearing);
		realignedMovement.x = -realignedMovement.x;
		realignedMovement.y = -realignedMovement.y;
		console.log("diffX: " + diffX + "; newX: " + realignedMovement.x);
		console.log("diffY: " + diffY + "; newY: " + realignedMovement.y);
		return realignedMovement;
	},

	calculateXMovement: function(touch) {
		if(World.isFlipXOn) { return (World.lastTouch.y - touch.y) * -1; }
        return (World.lastTouch.x - touch.x) * -1;
	},

	calculateYMovement: function(touch) {
		if(World.isFlipYOn) { return (World.lastTouch.x - touch.x) * -1; }
			return (World.lastTouch.y - touch.y) * -1;
	},

	checkOrientation: function() {
		if(window.orientation !== previousOrientation) {
			previousOrientation = window.orientation
			World.calculateAxes();
		}
	},

	setBearingExternally: function(bearing) {
		World.externalBearing = bearing;
//		console.log("World.externalBearing: " + World.externalBearing);
	},

	addInteractionEventListener: function addInteractionEventListenerFn() {
		document.getElementById("rotate_translate_anchor").addEventListener("click", World.rotateTranslateToggle);
		document.getElementById("raise_anchor").addEventListener("click", World.raiseButton);
		document.getElementById("lower_anchor").addEventListener("click", World.lowerButton);
		window.addEventListener("resize", World.checkOrientation, false);
		window.addEventListener("orientationchange", World.checkOrientation, false);
		document.getElementById("help_button_anchor").addEventListener("click", World.toggleHelpMessage);
		if(!World.helpMessageShows){
			document.getElementById(World.interactionContainer).addEventListener('touchstart', World.handleTouchStart, false);
            document.getElementById(World.interactionContainer).addEventListener('touchmove', World.handleTouchMove, false);
		}
		if(World.helpMessageShows){
			document.getElementById("close_x").addEventListener("click", World.toggleHelpMessage);
			document.getElementById(World.interactionContainer).removeEventListener('touchstart', World.handleTouchStart, false);
            document.getElementById(World.interactionContainer).removeEventListener('touchmove', World.handleTouchMove, false);
		}
	}

};

//World.init();

function readJSON() {
	$.getJSON("http://localhost:43770")
		.done(function(data) { World.jsonData = data;
			console.log("success, data.bearing: " + data.bearing);
		}).fail(function(jqxhr, textStatus, error) {
			var err = textStatus + ", " + error;
			console.log(err);
		});
}
//$(document).ready(function() {
//    setInterval(function() {
//        readJSON();
//    }, 250);
//});


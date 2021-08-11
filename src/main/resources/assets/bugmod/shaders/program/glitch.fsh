#version 120

varying vec2 texCoord;
uniform sampler2D DiffuseSampler;

void main() {
    // Get the location of the current pixel on the screen.
    // point.x ranges from 0 on the left to 1 on the right.
    // point.y ranges from 0 at the top to 1 at the bottom.
    // Change the numbers to grab values from other parts of the screen.
    vec2 point = texCoord.st;

    // Get the color of the pixel pointed to by the point variable.
    // color.r is red, color.g is green, color.b is blue, all values from 0 to 1.
    vec3 color = texture2D(DiffuseSampler, point).rgb;

    // You can do whatever you want to the color. Here we're inverting it.
    color.r = 1 - color.r;
    color.g = 1 - color.g;
    color.b = 1 - color.b;

    // Here's where we tell Minecraft what color we want this pixel.
    gl_FragColor = vec4(color, 1.0);
}

//// ---- gllock required fields -----------------------------------------------------------------------------------------
//#define RATE 0.75
//
//uniform float time;
//uniform float end;
//uniform sampler2D DiffuseSampler;
//uniform vec2 screenSize;
//// ---------------------------------------------------------------------------------------------------------------------
//
//float rand(vec2 co){
//    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453) * 2.0 - 1.0;
//}
//
//float offset(float blocks, vec2 uv) {
//    float shaderTime = 0.5*RATE;
//    return rand(vec2(shaderTime, floor(uv.y * blocks)));
//}
//
//void main(void) {
//    vec2 uv = vec2(1,-1)*gl_FragCoord.xy / screenSize;
//    gl_FragColor = texture2D(DiffuseSampler, uv);
//    gl_FragColor.r = texture2D(DiffuseSampler, uv + vec2(offset(64.0, uv) * 0.03, 0.0)).r;
//    gl_FragColor.g = texture2D(DiffuseSampler, uv + vec2(offset(64.0, uv) * 0.03 * 0.16666666, 0.0)).g;
//    gl_FragColor.b = texture2D(DiffuseSampler, uv + vec2(offset(64.0, uv) * 0.03, 0.0)).b;
//}

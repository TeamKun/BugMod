#version 120
uniform float Time;
uniform sampler2D DiffuseSampler;
varying vec2 texCoord;

// https://www.shadertoy.com/view/MlVSD3
float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453) * 2.0 - 1.0;
}

float offset(float blocks, vec2 uv) {
    return rand(vec2(Time, floor(uv.y * blocks)));
}

void main()
{
    gl_FragColor = texture2D(DiffuseSampler, texCoord);
    gl_FragColor.r = texture2D(DiffuseSampler, texCoord + vec2(offset(4.0, texCoord) * 0.1, 0.0)).r;
    gl_FragColor.g = texture2D(DiffuseSampler, texCoord + vec2(offset(2.0, texCoord) * 0.1 * 0.16666666, 0.0)).g;
    gl_FragColor.b = texture2D(DiffuseSampler, texCoord + vec2(offset(2.0, texCoord) * 0.1, 0.0)).b;

}
//#version 120
//
//#define iTime	time
//#define iResolution resolution
//
//uniform float time;
//uniform vec2 resolution;
//
//uniform sampler2D DiffuseSampler;
//
//uniform vec4 ColorModulate;
//
//varying vec2 texCoord;
//
///**
// * Convert r, g, b to normalized vec3
// */
//vec3 rgb(float r, float g, float b) {
//    return vec3(r / 255.0, g / 255.0, b / 255.0);
//}
//
///**
// * Draw a circle at vec2 `pos` with radius `rad` and
// * color `color`.
// */
//vec4 circle(vec2 uv, vec2 pos, float rad, vec3 color) {
//    float d = length(pos - uv) - rad;
//    float t = clamp(d, 0.0, 1.0);
//    return vec4(color, 1.0 - t);
//}
//
//void main() {
//    //gl_FragColor = texture2D(DiffuseSampler,texCoord) * ColorModulate;
//
//    vec2 uv = (gl_FragCoord.xy) / resolution.xy;
//    float gray = length(uv);
//    gl_FragColor = vec4(vec3(gray) , 1.0);
//
//    //vec2 uv = gl_FragCoord.xy;
//    //vec2 center = iResolution.xy * 0.5;
//    //float radius = 0.05 * iResolution.y;
//
//    //// Background layer
//    //vec4 layer1 = vec4(rgb(210.0, 222.0, 228.0), 1.0);
//
//    //// Circle
//    //vec3 red = rgb(225.0, 95.0, 60.0);
//    //vec4 layer2 = circle(uv, center, radius, red);
//
//    //// Blend the two
//    //gl_FragColor = layer2;
//}









//#version 120
//
//#define PI 3.1415
//
//uniform sampler2D DiffuseSampler;
//
//uniform vec4 ColorModulate;
//
//varying vec2 texCoord;
//
//mat2 rotate2d(float _angle){
//    return mat2(cos(_angle),-sin(_angle),
//    sin(_angle),cos(_angle));
//}
//
//float rand(vec2 st)
//{
//    return fract(sin(dot(st.xy, vec2(12.9898, 78.233))) * 10000.0);
//}
//
//mat2 scale(vec2 _scale){
//    return mat2(_scale.x,0.0,
//    0.0,_scale.y);
//}
//
//void main(){
//    gl_FragColor = texture2D(DiffuseSampler, fract(texCoord*rand(vec2(3.0)))) * ColorModulate;
//}
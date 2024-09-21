/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class javax_awt_BDFontMetrics */

#ifndef _WIN32
#undef JNIEXPORT
#define JNIEXPORT static
#endif

#ifndef _Included_javax_awt_BDFontMetrics
#define _Included_javax_awt_BDFontMetrics
#ifdef __cplusplus
extern "C" {
#endif
#undef javax_awt_BDFontMetrics_serialVersionUID
#define javax_awt_BDFontMetrics_serialVersionUID -4956160226949100590LL
/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    initN
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_javax_awt_BDFontMetrics_initN
  (JNIEnv *, jclass);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    destroyN
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_javax_awt_BDFontMetrics_destroyN
  (JNIEnv *, jclass, jlong);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    getFontFamilyAndStyleN
 * Signature: (JLjava/lang/String;)[Ljava/lang/String;
 */
JNIEXPORT jobjectArray JNICALL Java_javax_awt_BDFontMetrics_getFontFamilyAndStyleN
  (JNIEnv *, jclass, jlong, jstring);
  
/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    resolveFontN
 * Signature: (Ljava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_javax_awt_BDFontMetrics_resolveFontN
  (JNIEnv *, jclass, jstring, jint);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    unloadFontConfigN
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_javax_awt_BDFontMetrics_unloadFontConfigN
  (JNIEnv *, jclass);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    loadFontN
 * Signature: (JLjava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_javax_awt_BDFontMetrics_loadFontN
  (JNIEnv *, jobject, jlong, jstring, jint);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    destroyFontN
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_javax_awt_BDFontMetrics_destroyFontN
  (JNIEnv *, jobject, jlong);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    charWidthN
 * Signature: (JC)I
 */
JNIEXPORT jint JNICALL Java_javax_awt_BDFontMetrics_charWidthN
  (JNIEnv *, jobject, jlong, jchar);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    stringWidthN
 * Signature: (JLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_javax_awt_BDFontMetrics_stringWidthN
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     javax_awt_BDFontMetrics
 * Method:    charsWidthN
 * Signature: (J[CII)I
 */
JNIEXPORT jint JNICALL Java_javax_awt_BDFontMetrics_charsWidthN
  (JNIEnv *, jobject, jlong, jcharArray, jint, jint);

#ifdef __cplusplus
}
#endif
#endif

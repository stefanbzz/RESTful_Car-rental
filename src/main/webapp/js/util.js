/**
 * utility functions for multiple pages
 *
 * @author  Stefan Thomsen
 * @since   2019-03-12
 * @version 1.0
 */

/**
 * get the value of an url parameter identified by key
 * source: https://stackoverflow.com/a/25359264 by Reza Baradaran Gazorisangi & davidrl1000
 * @param key  the key to be searched
 * @returns values as a String or null if not found
 */
$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return null;
    }
    return decodeURI(results[1]) || 0;
}

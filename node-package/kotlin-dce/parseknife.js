(function (root, factory) {
  if (typeof define === 'function' && define.amd)
    define(['exports', 'kotlin'], factory);
  else if (typeof exports === 'object')
    factory(module.exports, require('kotlin'));
  else {
    if (typeof kotlin === 'undefined') {
      throw new Error("Error loading module 'parseknife'. Its dependency 'kotlin' was not found. Please, check whether 'kotlin' is loaded prior to 'parseknife'.");
    }
    root.parseknife = factory(typeof parseknife === 'undefined' ? {} : parseknife, kotlin);
  }
}(this, function (_, Kotlin) {
  'use strict';
  var Kind_OBJECT = Kotlin.Kind.OBJECT;
  var getOrNull = Kotlin.kotlin.text.getOrNull_94bcnn$;
  var unboxChar = Kotlin.unboxChar;
  var defineInlineFunction = Kotlin.defineInlineFunction;
  var last = Kotlin.kotlin.collections.last_us0mfu$;
  var Kind_CLASS = Kotlin.Kind.CLASS;
  var throwCCE = Kotlin.throwCCE;
  var LinkedHashMap = Kotlin.kotlin.collections.LinkedHashMap;
  var json = Kotlin.kotlin.js.json_pyyo18$;
  var copyToArray = Kotlin.kotlin.collections.copyToArray;
  var Enum = Kotlin.kotlin.Enum;
  var throwISE = Kotlin.throwISE;
  var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_ww73n8$;
  var ensureNotNull = Kotlin.ensureNotNull;
  var LinkedHashMap_init_0 = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$;
  var ArrayList_init_0 = Kotlin.kotlin.collections.ArrayList_init_287e2$;
  var Map = Kotlin.kotlin.collections.Map;
  var throwUPAE = Kotlin.throwUPAE;
  var equals = Kotlin.equals;
  var get_indices = Kotlin.kotlin.text.get_indices_gw00vp$;
  var Regex_init = Kotlin.kotlin.text.Regex_init_61zpoe$;
  var toBoxedChar = Kotlin.toBoxedChar;
  var Pair = Kotlin.kotlin.Pair;
  var mapOf = Kotlin.kotlin.collections.mapOf_qfcya0$;
  var toInt = Kotlin.kotlin.text.toInt_pdl1vz$;
  var Error_init = Kotlin.kotlin.Error_init_pdl1vj$;
  var Error_0 = Kotlin.kotlin.Error;
  var wrapFunction = Kotlin.wrapFunction;
  var joinToString = Kotlin.kotlin.collections.joinToString_fmv235$;
  var collectionSizeOrDefault = Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$;
  var copyOfRange = Kotlin.kotlin.collections.copyOfRange_5f8l3u$;
  var replace = Kotlin.kotlin.text.replace_680rmw$;
  var mapCapacity = Kotlin.kotlin.collections.mapCapacity_za3lpa$;
  var coerceAtLeast = Kotlin.kotlin.ranges.coerceAtLeast_dqglrj$;
  var LinkedHashMap_init_1 = Kotlin.kotlin.collections.LinkedHashMap_init_bwtc7$;
  var add = Kotlin.kotlin.js.add_g26eq9$;
  var iterator = Kotlin.kotlin.text.iterator_gw00vp$;
  var joinToString_0 = Kotlin.kotlin.collections.joinToString_cgipc5$;
  rExpression.prototype = Object.create(Rule.prototype);
  rExpression.prototype.constructor = rExpression;
  ParseKnifeError.prototype = Object.create(Error_0.prototype);
  ParseKnifeError.prototype.constructor = ParseKnifeError;
  OrphanedTokenError.prototype = Object.create(ParseKnifeError.prototype);
  OrphanedTokenError.prototype.constructor = OrphanedTokenError;
  QueryFailedError.prototype = Object.create(ParseKnifeError.prototype);
  QueryFailedError.prototype.constructor = QueryFailedError;
  AndRule.prototype = Object.create(Rule.prototype);
  AndRule.prototype.constructor = AndRule;
  AnyRule.prototype = Object.create(Rule.prototype);
  AnyRule.prototype.constructor = AnyRule;
  CharacterRule.prototype = Object.create(Rule.prototype);
  CharacterRule.prototype.constructor = CharacterRule;
  EofRule.prototype = Object.create(Rule.prototype);
  EofRule.prototype.constructor = EofRule;
  ManyRule.prototype = Object.create(Rule.prototype);
  ManyRule.prototype.constructor = ManyRule;
  MaybeRule.prototype = Object.create(Rule.prototype);
  MaybeRule.prototype.constructor = MaybeRule;
  NotRule.prototype = Object.create(Rule.prototype);
  NotRule.prototype.constructor = NotRule;
  OrRule.prototype = Object.create(Rule.prototype);
  OrRule.prototype.constructor = OrRule;
  RegexRule.prototype = Object.create(Rule.prototype);
  RegexRule.prototype.constructor = RegexRule;
  Rule$Companion$wrap$ObjectLiteral.prototype = Object.create(Rule.prototype);
  Rule$Companion$wrap$ObjectLiteral.prototype.constructor = Rule$Companion$wrap$ObjectLiteral;
  RuleInferenceError.prototype = Object.create(Error_0.prototype);
  RuleInferenceError.prototype.constructor = RuleInferenceError;
  function Cursor(source, _index) {
    Cursor$Companion_getInstance();
    if (_index === void 0)
      _index = null;
    this.source = source;
    this.index = _index != null ? _index : 0;
  }
  function Cursor$Companion() {
    Cursor$Companion_instance = this;
  }
  Cursor$Companion.prototype.make_4wem9b$ = function (source, index) {
    if (index === void 0)
      index = null;
    return new Cursor(new Source(source), index);
  };
  Cursor$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var Cursor$Companion_instance = null;
  function Cursor$Companion_getInstance() {
    if (Cursor$Companion_instance === null) {
      new Cursor$Companion();
    }
    return Cursor$Companion_instance;
  }
  Cursor.prototype.get_za3lpa$ = function (offset) {
    var tmp$, tmp$_0;
    tmp$_0 = (tmp$ = getOrNull(this.source.text, this.index + offset | 0)) != null ? String.fromCharCode(unboxChar(tmp$)) : null;
    if (tmp$_0 == null) {
      throw new ParseKnifeError(offset, 'Unexpected end of source');
    }
    return tmp$_0;
  };
  Cursor.prototype.plusAssign_za3lpa$ = function (i) {
    this.index = this.index + i | 0;
  };
  Cursor.prototype.consume_rtncj4$ = function (r) {
    var t = r.makeToken_qf6h0s$(this);
    this.index = this.index + t.value.length | 0;
    return t;
  };
  Cursor.prototype.branch_tt0fr9$ = defineInlineFunction('parseknife.dev.nickmatt.parseknife.Cursor.branch_tt0fr9$', function (meth) {
    var start = this.index;
    var children;
    try {
      children = meth();
    }finally {
      this.index = start;
    }
    return this.makeTokenFromChildren(children.slice());
  });
  Cursor.prototype.makeTokenFromChildren = function (children) {
    var last_0 = last(children);
    var result = Token$Companion_getInstance().make_qt3jon$(this.source, this.index, last_0.index + last_0.value.length - this.index | 0);
    return result.withChildren_8oyomw$(children.slice());
  };
  Cursor.prototype.makeToken_za3lpa$ = function (length) {
    if (length === void 0)
      length = 1;
    return Token$Companion_getInstance().make_qt3jon$(this.source, this.index, length);
  };
  Cursor.$metadata$ = {kind: Kind_CLASS, simpleName: 'Cursor', interfaces: []};
  function main() {
  }
  var r;
  function rExpression() {
    rExpression_instance = this;
    Rule.call(this);
  }
  rExpression.prototype.test_qf6h0s$ = function (cursor) {
    return cursor.makeTokenFromChildren([_expression.makeToken_qf6h0s$(cursor)]);
  };
  rExpression.$metadata$ = {kind: Kind_OBJECT, simpleName: 'rExpression', interfaces: [Rule]};
  var rExpression_instance = null;
  function rExpression_getInstance() {
    if (rExpression_instance === null) {
      new rExpression();
    }
    return rExpression_instance;
  }
  var eofTerm;
  var integer;
  var character;
  var string;
  var regex;
  var group;
  var ruleName;
  var termValue;
  var decorator;
  var term;
  var _expression;
  var language;
  var metaParser_instance = null;
  var TermKind$END_OF_FILE_instance;
  var TermKind$INTEGER_instance;
  var TermKind$CHARACTER_instance;
  var TermKind$STRING_instance;
  var TermKind$REGEX_instance;
  var TermKind$GROUP_instance;
  var TermKind$RULE_NAME_instance;
  var TermKind$Companion_instance = null;
  var TransformTable$Companion_instance = null;
  var r_0;
  var r_1;
  var REGEX_ESCAPE_REGEX;
  var ESCAPE_REGEX;
  var ESCAPE_CODES;
  function OrphanedTokenError(token) {
    ParseKnifeError_init_2(token, 'Could not find parent for token', this);
    this.name = 'OrphanedTokenError';
  }
  OrphanedTokenError.$metadata$ = {kind: Kind_CLASS, simpleName: 'OrphanedTokenError', interfaces: [ParseKnifeError]};
  function ParseKnifeError(index, _message) {
    ParseKnifeError$Companion_getInstance();
    Error_init(_message, this);
    this.index = index;
    this.name = 'ParseKnifeError';
  }
  function ParseKnifeError$Companion() {
    ParseKnifeError$Companion_instance = this;
  }
  ParseKnifeError$Companion.prototype.makeMessage_nsgxaj$ = function (index, source, message) {
    var tmp$ = source.makeCoords_za3lpa$(index);
    var line = tmp$.component1(), column = tmp$.component2();
    return '(' + line + ', ' + column + ') ' + message;
  };
  ParseKnifeError$Companion.prototype.makeMessage_rklfvd$ = function (index, source, expected) {
    return this.makeMessage_nsgxaj$(index, source, 'Expected: ' + expected);
  };
  ParseKnifeError$Companion.prototype.doUntil_2b628o$ = defineInlineFunction('parseknife.dev.nickmatt.parseknife.ParseKnifeError.Companion.doUntil_2b628o$', wrapFunction(function () {
    var ParseKnifeError = _.dev.nickmatt.parseknife.ParseKnifeError;
    return function (atLeastOnce, meth) {
      if (atLeastOnce === void 0)
        atLeastOnce = true;
      if (atLeastOnce)
        meth();
      while (true)
        try {
          meth();
        } catch (e) {
          if (Kotlin.isType(e, ParseKnifeError)) {
            return;
          } else
            throw e;
        }
    };
  }));
  ParseKnifeError$Companion.prototype.collectUntil_nkfre5$ = defineInlineFunction('parseknife.dev.nickmatt.parseknife.ParseKnifeError.Companion.collectUntil_nkfre5$', wrapFunction(function () {
    var ArrayList_init = Kotlin.kotlin.collections.ArrayList_init_287e2$;
    var ParseKnifeError = _.dev.nickmatt.parseknife.ParseKnifeError;
    var copyToArray = Kotlin.kotlin.collections.copyToArray;
    return function (T_0, isT, atLeastOnce, meth) {
      if (atLeastOnce === void 0)
        atLeastOnce = true;
      var result = ArrayList_init();
      doUntil_2b628o$break: do {
        if (atLeastOnce) {
          result.add_11rb$(meth());
        }
        while (true)
          try {
            result.add_11rb$(meth());
          } catch (e) {
            if (Kotlin.isType(e, ParseKnifeError)) {
              break doUntil_2b628o$break;
            } else
              throw e;
          }
      }
       while (false);
      return copyToArray(result);
    };
  }));
  ParseKnifeError$Companion.prototype.doCatching_566hdc$ = function (reject, meth) {
    try {
      return meth();
    } catch (e) {
      if (Kotlin.isType(e, ParseKnifeError)) {
        reject != null ? reject(e) : null;
        return null;
      } else
        throw e;
    }
  };
  ParseKnifeError$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var ParseKnifeError$Companion_instance = null;
  function ParseKnifeError$Companion_getInstance() {
    if (ParseKnifeError$Companion_instance === null) {
      new ParseKnifeError$Companion();
    }
    return ParseKnifeError$Companion_instance;
  }
  ParseKnifeError.$metadata$ = {kind: Kind_CLASS, simpleName: 'ParseKnifeError', interfaces: [Error_0]};
  function ParseKnifeError_init(_index, _message, source, $this) {
    $this = $this || Object.create(ParseKnifeError.prototype);
    ParseKnifeError.call($this, _index, ParseKnifeError$Companion_getInstance().makeMessage_nsgxaj$(_index, source, _message));
    return $this;
  }
  function ParseKnifeError_init_1(_index, expected, source, $this) {
    $this = $this || Object.create(ParseKnifeError.prototype);
    ParseKnifeError.call($this, _index, ParseKnifeError$Companion_getInstance().makeMessage_rklfvd$(_index, source, expected));
    return $this;
  }
  function ParseKnifeError_init_2(token, _message, $this) {
    $this = $this || Object.create(ParseKnifeError.prototype);
    ParseKnifeError_init(token.index, _message, token.source, $this);
    return $this;
  }
  function ParseKnifeError_init_4(cursor, _message, $this) {
    $this = $this || Object.create(ParseKnifeError.prototype);
    ParseKnifeError_init(cursor.index, _message, cursor.source, $this);
    return $this;
  }
  function ParseKnifeError_init_5(cursor, expected, $this) {
    $this = $this || Object.create(ParseKnifeError.prototype);
    ParseKnifeError_init_1(cursor.index, expected, cursor.source, $this);
    return $this;
  }
  var Parser$Companion_instance = null;
  function QueryFailedError(token) {
    ParseKnifeError_init_2(token, 'Could not find child matching query', this);
    this.name = 'QueryFailedError';
  }
  QueryFailedError.$metadata$ = {kind: Kind_CLASS, simpleName: 'QueryFailedError', interfaces: [ParseKnifeError]};
  function AndRule(_children) {
    Rule.call(this);
    var destination = ArrayList_init(_children.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== _children.length; ++tmp$) {
      var item = _children[tmp$];
      destination.add_11rb$(Rule$Companion_getInstance().infer_jiburq$([item]));
    }
    this.children_dt4310$_0 = destination;
    this.whitespaceSensitive_36yt80$_0 = false;
  }
  AndRule.prototype.test_qf6h0s$ = function (cursor) {
    var start = cursor.index;
    var children;
    try {
      var $receiver = this.children_dt4310$_0;
      var destination = ArrayList_init(collectionSizeOrDefault($receiver, 10));
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var item = tmp$.next();
        var tmp$_0 = destination.add_11rb$;
        if (!this.whitespaceSensitive_36yt80$_0)
          cursor.consume_rtncj4$(RegexRule$Companion_getInstance().WHITESPACE);
        tmp$_0.call(destination, cursor.consume_rtncj4$(item));
      }
      children = copyToArray(destination);
    }finally {
      cursor.index = start;
    }
    return cursor.makeTokenFromChildren(children.slice());
  };
  AndRule.prototype.withWhitespaceSensitivity = function () {
    this.whitespaceSensitive_36yt80$_0 = true;
    return this;
  };
  AndRule.prototype.toString = function () {
    var result = '(' + joinToString(this.children_dt4310$_0, ' ') + ')';
    if (this.whitespaceSensitive_36yt80$_0)
      result += '^';
    return result;
  };
  AndRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'AndRule', interfaces: [Rule]};
  function AnyRule(_length) {
    if (_length === void 0)
      _length = null;
    Rule.call(this);
    this.length_a9eb2e$_0 = _length != null ? _length : 1;
  }
  AnyRule.prototype.test_qf6h0s$ = function (cursor) {
    if ((cursor.source.text.length - cursor.index | 0) < this.length_a9eb2e$_0)
      throw ParseKnifeError_init_4(cursor, 'Expected ' + this.length_a9eb2e$_0 + ' more characters');
    return cursor.makeToken_za3lpa$(this.length_a9eb2e$_0);
  };
  AnyRule.prototype.toString = function () {
    return this.length_a9eb2e$_0.toString();
  };
  AnyRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'AnyRule', interfaces: [Rule]};
  function CharacterRule(expected) {
    CharacterRule$Companion_getInstance();
    Rule.call(this);
    this.expected_6wx691$_0 = expected;
  }
  function CharacterRule$Companion() {
    CharacterRule$Companion_instance = this;
  }
  CharacterRule$Companion.prototype.make_s8itvh$ = function (expected) {
    return new CharacterRule(String.fromCharCode(expected));
  };
  CharacterRule$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var CharacterRule$Companion_instance = null;
  function CharacterRule$Companion_getInstance() {
    if (CharacterRule$Companion_instance === null) {
      new CharacterRule$Companion();
    }
    return CharacterRule$Companion_instance;
  }
  CharacterRule.prototype.test_qf6h0s$ = function (cursor) {
    var received = cursor.get_za3lpa$(0);
    if (!equals(received, this.expected_6wx691$_0))
      throw ParseKnifeError_init_5(cursor, this);
    return cursor.makeToken_za3lpa$();
  };
  CharacterRule.prototype.toString = function () {
    return "'" + this.expected_6wx691$_0 + "'";
  };
  CharacterRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'CharacterRule', interfaces: [Rule]};
  function EofRule() {
    EofRule$Companion_getInstance();
    Rule.call(this);
  }
  function EofRule$Companion() {
    EofRule$Companion_instance = this;
    this.instance = new EofRule();
  }
  EofRule$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var EofRule$Companion_instance = null;
  function EofRule$Companion_getInstance() {
    if (EofRule$Companion_instance === null) {
      new EofRule$Companion();
    }
    return EofRule$Companion_instance;
  }
  EofRule.prototype.test_qf6h0s$ = function (cursor) {
    if (cursor.index !== cursor.source.text.length)
      throw ParseKnifeError_init_4(cursor, 'Expected end of file');
    return cursor.makeToken_za3lpa$(0);
  };
  EofRule.prototype.toString = function () {
    return '$eof';
  };
  EofRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'EofRule', interfaces: [Rule]};
  function ManyRule(_root) {
    Rule.call(this);
    this.root_ljfajd$_0 = Rule$Companion_getInstance().infer_jiburq$([_root]);
  }
  ManyRule.prototype.test_qf6h0s$ = function (cursor) {
    var start = cursor.index;
    var children;
    try {
      var result = ArrayList_init_0();
      doUntil_2b628o$break: do {
        if (true) {
          result.add_11rb$(cursor.consume_rtncj4$(this.root_ljfajd$_0));
        }
        while (true)
          try {
            result.add_11rb$(cursor.consume_rtncj4$(this.root_ljfajd$_0));
          } catch (e) {
            if (Kotlin.isType(e, ParseKnifeError)) {
              break doUntil_2b628o$break;
            } else
              throw e;
          }
      }
       while (false);
      children = copyToArray(result);
    }finally {
      cursor.index = start;
    }
    return cursor.makeTokenFromChildren(children.slice());
  };
  ManyRule.prototype.toString = function () {
    return this.root_ljfajd$_0.toString() + '+';
  };
  ManyRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'ManyRule', interfaces: [Rule]};
  function MaybeRule(_root) {
    Rule.call(this);
    this.root_u5w5ja$_0 = Rule$Companion_getInstance().infer_jiburq$([_root]);
  }
  MaybeRule.prototype.test_qf6h0s$ = function (cursor) {
    try {
      return cursor.makeTokenFromChildren([this.root_u5w5ja$_0.makeToken_qf6h0s$(cursor)]);
    } catch (e) {
      if (Kotlin.isType(e, ParseKnifeError)) {
        return cursor.makeToken_za3lpa$(0);
      } else
        throw e;
    }
  };
  MaybeRule.prototype.toString = function () {
    return this.root_u5w5ja$_0.toString() + '?';
  };
  MaybeRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'MaybeRule', interfaces: [Rule]};
  function NotRule(_root) {
    Rule.call(this);
    this.root_btluhx$_0 = Rule$Companion_getInstance().infer_jiburq$([_root]);
  }
  NotRule.prototype.test_qf6h0s$ = function (cursor) {
    try {
      this.root_btluhx$_0.makeToken_qf6h0s$(cursor);
    } catch (e) {
      if (Kotlin.isType(e, ParseKnifeError)) {
        if (cursor.index >= cursor.source.text.length)
          throw ParseKnifeError_init_5(cursor, this);
        return cursor.makeToken_za3lpa$(1);
      } else
        throw e;
    }
    throw ParseKnifeError_init_5(cursor, this);
  };
  NotRule.prototype.toString = function () {
    return this.root_btluhx$_0.toString() + '!';
  };
  NotRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'NotRule', interfaces: [Rule]};
  function OrRule(_children) {
    Rule.call(this);
    var destination = ArrayList_init(_children.length);
    var tmp$;
    for (tmp$ = 0; tmp$ !== _children.length; ++tmp$) {
      var item = _children[tmp$];
      destination.add_11rb$(Rule$Companion_getInstance().infer_jiburq$([item]));
    }
    this.children_xzofa8$_0 = destination;
  }
  OrRule.prototype.test_qf6h0s$ = function (cursor) {
    var tmp$;
    var errors = ArrayList_init_0();
    tmp$ = this.children_xzofa8$_0.iterator();
    while (tmp$.hasNext()) {
      var child = tmp$.next();
      try {
        var result = cursor.makeTokenFromChildren([child.makeToken_qf6h0s$(cursor)]);
        return result;
      } catch (e) {
        if (Kotlin.isType(e, ParseKnifeError)) {
          errors.add_11rb$(e);
        } else
          throw e;
      }
    }
    var maxByOrNull$result;
    maxByOrNull$break: do {
      var iterator = errors.iterator();
      if (!iterator.hasNext()) {
        maxByOrNull$result = null;
        break maxByOrNull$break;
      }
      var maxElem = iterator.next();
      if (!iterator.hasNext()) {
        maxByOrNull$result = maxElem;
        break maxByOrNull$break;
      }
      var maxValue = maxElem.index;
      do {
        var e_0 = iterator.next();
        var v = e_0.index;
        if (Kotlin.compareTo(maxValue, v) < 0) {
          maxElem = e_0;
          maxValue = v;
        }
      }
       while (iterator.hasNext());
      maxByOrNull$result = maxElem;
    }
     while (false);
    throw ensureNotNull(maxByOrNull$result);
  };
  OrRule.prototype.toString = function () {
    return '(' + joinToString(this.children_xzofa8$_0, ' | ') + ')';
  };
  OrRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'OrRule', interfaces: [Rule]};
  function RegexRule(_expression) {
    RegexRule$Companion_getInstance();
    Rule.call(this);
    this.expression_5injbx$_0 = new RegExp(_expression, 'y');
  }
  function RegexRule$Companion() {
    RegexRule$Companion_instance = this;
    this.WHITESPACE = new RegexRule('\\s*');
  }
  RegexRule$Companion.prototype.make_qabkwx$ = function (_expression) {
    return new RegexRule(_expression.source);
  };
  RegexRule$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var RegexRule$Companion_instance = null;
  function RegexRule$Companion_getInstance() {
    if (RegexRule$Companion_instance === null) {
      new RegexRule$Companion();
    }
    return RegexRule$Companion_instance;
  }
  RegexRule.prototype.test_qf6h0s$ = function (cursor) {
    var tmp$;
    tmp$ = this.makeMatch_abenbq$_0(cursor);
    if (tmp$ == null) {
      throw ParseKnifeError_init_5(cursor, this);
    }
    var numberedGroups = tmp$.component1(), namedGroups = tmp$.component2();
    var token = new Token(cursor.source, cursor.index, numberedGroups[0]);
    if (numberedGroups.length > 1)
      token.withMeta_4w9ihe$('numberedGroups', copyOfRange(numberedGroups, 1, numberedGroups.length));
    if ((namedGroups != null ? !namedGroups.isEmpty() : null) === true)
      token.withMeta_4w9ihe$('namedGroups', namedGroups);
    return token;
  };
  RegexRule.prototype.toString = function () {
    return '/' + replace(this.expression_5injbx$_0.toString(), '/', '\\/') + '/';
  };
  RegexRule.prototype.makeMatch_abenbq$_0 = function (c) {
    var tmp$, tmp$_0;
    this.expression_5injbx$_0.lastIndex = c.index;
    tmp$ = this.expression_5injbx$_0.exec(c.source.text);
    if (tmp$ == null) {
      return null;
    }
    var jsMatch = tmp$;
    var numberedGroups = Array.from(jsMatch);
    var tmp$_1;
    if ((tmp$_0 = jsMatch.groups) != null) {
      var groupNames = Object.keys(tmp$_0);
      var result = LinkedHashMap_init_1(coerceAtLeast(mapCapacity(groupNames.length), 16));
      var tmp$_2;
      for (tmp$_2 = 0; tmp$_2 !== groupNames.length; ++tmp$_2) {
        var element = groupNames[tmp$_2];
        result.put_xwzc9p$(element, tmp$_0[element]);
      }
      tmp$_1 = result;
    } else
      tmp$_1 = null;
    var namedGroups = tmp$_1;
    return new Pair(numberedGroups, namedGroups);
  };
  RegexRule.$metadata$ = {kind: Kind_CLASS, simpleName: 'RegexRule', interfaces: [Rule]};
  function Rule() {
    Rule$Companion_getInstance();
    this.meta_3d839z$_0 = json([]);
  }
  function Rule$Companion() {
    Rule$Companion_instance = this;
  }
  Rule$Companion.prototype.infer_jiburq$ = function (args) {
    var tmp$;
    if (args.length > 1) {
      var destination = ArrayList_init(args.length);
      var tmp$_0;
      for (tmp$_0 = 0; tmp$_0 !== args.length; ++tmp$_0) {
        var item = args[tmp$_0];
        destination.add_11rb$(this.infer_jiburq$([item]));
      }
      return new AndRule(copyToArray(destination).slice());
    }
    var arg = args[0];
    if (Kotlin.isType(arg, Rule))
      tmp$ = arg;
    else if (typeof arg === 'number')
      tmp$ = new AnyRule(arg);
    else if (Kotlin.isChar(arg))
      tmp$ = CharacterRule$Companion_getInstance().make_s8itvh$(unboxChar(arg));
    else if (typeof arg === 'string') {
      var tmp$_1;
      if (arg.length === 1)
        tmp$_1 = new CharacterRule(arg);
      else {
        var destination_0 = ArrayList_init(arg.length);
        var tmp$_2;
        tmp$_2 = iterator(arg);
        while (tmp$_2.hasNext()) {
          var item_0 = unboxChar(tmp$_2.next());
          var tmp$_3 = destination_0.add_11rb$;
          var it = toBoxedChar(item_0);
          tmp$_3.call(destination_0, CharacterRule$Companion_getInstance().make_s8itvh$(unboxChar(it)));
        }
        tmp$_1 = new AndRule(copyToArray(destination_0).slice());
      }
      tmp$ = tmp$_1;
    } else if (Kotlin.isType(arg, RegExp))
      tmp$ = RegexRule$Companion_getInstance().make_qabkwx$(arg);
    else
      throw new RuleInferenceError(arg);
    return tmp$;
  };
  Rule$Companion.prototype.refer_g1e054$ = defineInlineFunction('parseknife.dev.nickmatt.parseknife.rule.Rule.Companion.refer_g1e054$', wrapFunction(function () {
    var throwUPAE = Kotlin.throwUPAE;
    var Rule = _.dev.nickmatt.parseknife.rule.Rule;
    var Kind_CLASS = Kotlin.Kind.CLASS;
    Rule$Companion$refer$ObjectLiteral.prototype = Object.create(Rule.prototype);
    Rule$Companion$refer$ObjectLiteral.prototype.constructor = Rule$Companion$refer$ObjectLiteral;
    function Rule$Companion$refer$ObjectLiteral(closure$resolve) {
      this.closure$resolve = closure$resolve;
      Rule.call(this);
      this._root_4ec3dc$_0 = this._root_4ec3dc$_0;
    }
    Object.defineProperty(Rule$Companion$refer$ObjectLiteral.prototype, '_root', {configurable: true, get: function () {
      if (this._root_4ec3dc$_0 == null)
        return throwUPAE('_root');
      return this._root_4ec3dc$_0;
    }, set: function (_root) {
      this._root_4ec3dc$_0 = _root;
    }});
    Object.defineProperty(Rule$Companion$refer$ObjectLiteral.prototype, 'root', {configurable: true, get: function () {
      if (this._root_4ec3dc$_0 == null)
        this._root = this.closure$resolve();
      return this._root;
    }});
    Rule$Companion$refer$ObjectLiteral.prototype.test_qf6h0s$ = function (cursor) {
      return this.root.makeToken_qf6h0s$(cursor);
    };
    Rule$Companion$refer$ObjectLiteral.prototype.toString = function () {
      return this.root.toString();
    };
    Rule$Companion$refer$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Rule]};
    return function (resolve) {
      return new Rule$Companion$refer$ObjectLiteral(resolve);
    };
  }));
  function Rule$Companion$wrap$ObjectLiteral(closure$root) {
    this.closure$root = closure$root;
    Rule.call(this);
  }
  Rule$Companion$wrap$ObjectLiteral.prototype.test_qf6h0s$ = function (cursor) {
    return cursor.makeTokenFromChildren([this.closure$root.test_qf6h0s$(cursor)]);
  };
  Rule$Companion$wrap$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Rule]};
  Rule$Companion.prototype.wrap_rtncj4$ = function (root) {
    return new Rule$Companion$wrap$ObjectLiteral(root);
  };
  Rule$Companion.prototype.make_e8dtql$ = defineInlineFunction('parseknife.dev.nickmatt.parseknife.rule.Rule.Companion.make_e8dtql$', wrapFunction(function () {
    var Rule = _.dev.nickmatt.parseknife.rule.Rule;
    var Kind_CLASS = Kotlin.Kind.CLASS;
    Rule$Companion$make$ObjectLiteral.prototype = Object.create(Rule.prototype);
    Rule$Companion$make$ObjectLiteral.prototype.constructor = Rule$Companion$make$ObjectLiteral;
    function Rule$Companion$make$ObjectLiteral(closure$_test) {
      this.closure$_test = closure$_test;
      Rule.call(this);
    }
    Rule$Companion$make$ObjectLiteral.prototype.test_qf6h0s$ = function (cursor) {
      return this.closure$_test(cursor);
    };
    Rule$Companion$make$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Rule]};
    return function (_test) {
      return new Rule$Companion$make$ObjectLiteral(_test);
    };
  }));
  Rule$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var Rule$Companion_instance = null;
  function Rule$Companion_getInstance() {
    if (Rule$Companion_instance === null) {
      new Rule$Companion();
    }
    return Rule$Companion_instance;
  }
  Object.defineProperty(Rule.prototype, 'meta', {configurable: true, get: function () {
    return this.meta_3d839z$_0;
  }});
  Rule.prototype.withMeta_bm4g0d$ = function (key, value) {
    this.meta[key] = value;
    return this;
  };
  Rule.prototype.makeToken_qf6h0s$ = function (c) {
    var t = this.test_qf6h0s$(c);
    add(t.meta, this.meta);
    return t;
  };
  Rule.prototype.match = function (source, index) {
    if (index === void 0)
      index = null;
    return this.makeToken_qf6h0s$(Cursor$Companion_getInstance().make_4wem9b$(source, index));
  };
  Rule.prototype.matchEntire_61zpoe$ = function (source) {
    var cursor = Cursor$Companion_getInstance().make_4wem9b$(source);
    var result = cursor.consume_rtncj4$(this);
    return cursor.index !== source.length ? null : result;
  };
  Rule.$metadata$ = {kind: Kind_CLASS, simpleName: 'Rule', interfaces: []};
  function RuleHelper() {
    RuleHelper$Companion_getInstance();
  }
  function RuleHelper$Companion() {
    RuleHelper$Companion_instance = this;
    this.instance = new RuleHelper();
  }
  RuleHelper$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var RuleHelper$Companion_instance = null;
  function RuleHelper$Companion_getInstance() {
    if (RuleHelper$Companion_instance === null) {
      new RuleHelper$Companion();
    }
    return RuleHelper$Companion_instance;
  }
  RuleHelper.prototype.invoke_jiburq$ = function (args) {
    return Rule$Companion_getInstance().infer_jiburq$(args.slice());
  };
  RuleHelper.prototype.invoke = function (args) {
    return Rule$Companion_getInstance().infer_jiburq$(args.slice());
  };
  RuleHelper.prototype.ref = defineInlineFunction('parseknife.dev.nickmatt.parseknife.rule.RuleHelper.ref', wrapFunction(function () {
    var Rule = _.dev.nickmatt.parseknife.rule.Rule;
    var throwUPAE = Kotlin.throwUPAE;
    var Kind_CLASS = Kotlin.Kind.CLASS;
    Rule$Companion$refer$ObjectLiteral.prototype = Object.create(Rule.prototype);
    Rule$Companion$refer$ObjectLiteral.prototype.constructor = Rule$Companion$refer$ObjectLiteral;
    function Rule$Companion$refer$ObjectLiteral(closure$resolve) {
      this.closure$resolve = closure$resolve;
      Rule.call(this);
      this._root_4ec3dc$_0 = this._root_4ec3dc$_0;
    }
    Object.defineProperty(Rule$Companion$refer$ObjectLiteral.prototype, '_root', {configurable: true, get: function () {
      if (this._root_4ec3dc$_0 == null)
        return throwUPAE('_root');
      return this._root_4ec3dc$_0;
    }, set: function (_root) {
      this._root_4ec3dc$_0 = _root;
    }});
    Object.defineProperty(Rule$Companion$refer$ObjectLiteral.prototype, 'root', {configurable: true, get: function () {
      if (this._root_4ec3dc$_0 == null)
        this._root = this.closure$resolve();
      return this._root;
    }});
    Rule$Companion$refer$ObjectLiteral.prototype.test_qf6h0s$ = function (cursor) {
      return this.root.makeToken_qf6h0s$(cursor);
    };
    Rule$Companion$refer$ObjectLiteral.prototype.toString = function () {
      return this.root.toString();
    };
    Rule$Companion$refer$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Rule]};
    return function (resolve) {
      return new Rule$Companion$refer$ObjectLiteral(resolve);
    };
  }));
  RuleHelper.prototype.make = defineInlineFunction('parseknife.dev.nickmatt.parseknife.rule.RuleHelper.make', wrapFunction(function () {
    var Rule = _.dev.nickmatt.parseknife.rule.Rule;
    var Kind_CLASS = Kotlin.Kind.CLASS;
    Rule$Companion$make$ObjectLiteral.prototype = Object.create(Rule.prototype);
    Rule$Companion$make$ObjectLiteral.prototype.constructor = Rule$Companion$make$ObjectLiteral;
    function Rule$Companion$make$ObjectLiteral(closure$_test) {
      this.closure$_test = closure$_test;
      Rule.call(this);
    }
    Rule$Companion$make$ObjectLiteral.prototype.test_qf6h0s$ = function (cursor) {
      return this.closure$_test(cursor);
    };
    Rule$Companion$make$ObjectLiteral.$metadata$ = {kind: Kind_CLASS, interfaces: [Rule]};
    return function (test) {
      return new Rule$Companion$make$ObjectLiteral(test);
    };
  }));
  RuleHelper.prototype.wrap = function (r) {
    return Rule$Companion_getInstance().wrap_rtncj4$(r);
  };
  RuleHelper.prototype.any = function (length) {
    if (length === void 0)
      length = null;
    return new AnyRule(length);
  };
  RuleHelper.prototype.char = function (c) {
    return new CharacterRule(c);
  };
  RuleHelper.prototype.and_jiburq$ = function (r) {
    return new AndRule(r.slice());
  };
  RuleHelper.prototype.and = function (r) {
    return new AndRule(r.slice());
  };
  RuleHelper.prototype.or_jiburq$ = function (r) {
    return new OrRule(r.slice());
  };
  RuleHelper.prototype.or = function (r) {
    return new OrRule(r.slice());
  };
  RuleHelper.prototype.many = function (r) {
    return new ManyRule(r);
  };
  RuleHelper.prototype.maybe = function (r) {
    return new MaybeRule(r);
  };
  RuleHelper.prototype.not = function (r) {
    return new NotRule(r);
  };
  RuleHelper.prototype.eof = function () {
    return EofRule$Companion_getInstance().instance;
  };
  RuleHelper.prototype.regexWrap = function (expression) {
    return RegexRule$Companion_getInstance().make_qabkwx$(expression);
  };
  RuleHelper.prototype.regex = function (expression) {
    return new RegexRule(expression);
  };
  RuleHelper.$metadata$ = {kind: Kind_CLASS, simpleName: 'RuleHelper', interfaces: []};
  function RuleInferenceError(received) {
    Error_init('Expected rule literal (char, string, regex, etc.)', this);
    this.received = received;
    this.name = 'RuleInferenceError';
  }
  RuleInferenceError.$metadata$ = {kind: Kind_CLASS, simpleName: 'RuleInferenceError', interfaces: [Error_0]};
  function Source(text) {
    this.text = text;
  }
  Source.prototype.get_vux9f0$ = function (index, length) {
    var $receiver = this.text;
    var endIndex = index + length | 0;
    return $receiver.substring(index, endIndex);
  };
  Source.prototype.makeCoords_za3lpa$ = function (index) {
    var lineBreakIndex = 0;
    var line = 1;
    for (var i = 0; i < index; i++)
      if (this.text.charCodeAt(i) === 10) {
        lineBreakIndex = i + 1 | 0;
        line = line + 1 | 0;
      }
    return new SourceLocation(line, index + 1 - lineBreakIndex | 0);
  };
  Source.$metadata$ = {kind: Kind_CLASS, simpleName: 'Source', interfaces: []};
  Source.prototype.component1 = function () {
    return this.text;
  };
  Source.prototype.copy_61zpoe$ = function (text) {
    return new Source(text === void 0 ? this.text : text);
  };
  Source.prototype.toString = function () {
    return 'Source(text=' + Kotlin.toString(this.text) + ')';
  };
  Source.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.text) | 0;
    return result;
  };
  Source.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && Kotlin.equals(this.text, other.text))));
  };
  function SourceLocation(line, column) {
    this.line = line;
    this.column = column;
  }
  SourceLocation.$metadata$ = {kind: Kind_CLASS, simpleName: 'SourceLocation', interfaces: []};
  SourceLocation.prototype.component1 = function () {
    return this.line;
  };
  SourceLocation.prototype.component2 = function () {
    return this.column;
  };
  SourceLocation.prototype.copy_vux9f0$ = function (line, column) {
    return new SourceLocation(line === void 0 ? this.line : line, column === void 0 ? this.column : column);
  };
  SourceLocation.prototype.toString = function () {
    return 'SourceLocation(line=' + Kotlin.toString(this.line) + (', column=' + Kotlin.toString(this.column)) + ')';
  };
  SourceLocation.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.line) | 0;
    result = result * 31 + Kotlin.hashCode(this.column) | 0;
    return result;
  };
  SourceLocation.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.line, other.line) && Kotlin.equals(this.column, other.column)))));
  };
  function Token(source, index, value) {
    Token$Companion_getInstance();
    this.source = source;
    this.index = index;
    this.value = value;
    this.meta = json([]);
    this.children_p0ii3s$_0 = [];
    this.parentOrNull = null;
  }
  function Token$Companion() {
    Token$Companion_instance = this;
  }
  Token$Companion.prototype.make_qt3jon$ = function (source, index, length) {
    return new Token(source, index, source.get_vux9f0$(index, length));
  };
  Token$Companion.$metadata$ = {kind: Kind_OBJECT, simpleName: 'Companion', interfaces: []};
  var Token$Companion_instance = null;
  function Token$Companion_getInstance() {
    if (Token$Companion_instance === null) {
      new Token$Companion();
    }
    return Token$Companion_instance;
  }
  Object.defineProperty(Token.prototype, 'children', {configurable: true, get: function () {
    return this.children_p0ii3s$_0;
  }, set: function (tokens) {
    var tmp$;
    for (tmp$ = 0; tmp$ !== tokens.length; ++tmp$) {
      var t = tokens[tmp$];
      t.parent = this;
    }
    this.children_p0ii3s$_0 = tokens;
  }});
  Object.defineProperty(Token.prototype, 'parent', {configurable: true, get: function () {
    var tmp$;
    tmp$ = this.parentOrNull;
    if (tmp$ == null) {
      throw new OrphanedTokenError(this);
    }
    return tmp$;
  }, set: function (value) {
    this.parentOrNull = value;
  }});
  Token.prototype.withChildren_8oyomw$ = function (tokens) {
    this.children = this.children.concat(tokens);
    return this;
  };
  Token.prototype.withMeta_4w9ihe$ = function (k, v) {
    this.meta[k] = v;
    return this;
  };
  Token.prototype.walkAtDepth = function (maxDepth, meth) {
    if (maxDepth === void 0)
      maxDepth = null;
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var targets = [this];
    var nextTargets = ArrayList_init_0();
    var depth = 1;
    while (!(maxDepth != null && depth > maxDepth)) {
      tmp$ = targets;
      for (tmp$_0 = 0; tmp$_0 !== tmp$.length; ++tmp$_0) {
        var t = tmp$[tmp$_0];
        tmp$_1 = t.children;
        for (tmp$_2 = 0; tmp$_2 !== tmp$_1.length; ++tmp$_2) {
          var c = tmp$_1[tmp$_2];
          var result = meth(c);
          if (result === false)
            return;
          if (result === true)
            continue;
          nextTargets.add_11rb$(c);
        }
      }
      if (nextTargets.isEmpty())
        break;
      targets = copyToArray(nextTargets);
      nextTargets.clear();
      depth = depth + 1 | 0;
    }
  };
  Token.prototype.walk = function (meth) {
    this.walkAtDepth(null, meth);
  };
  function Token$queryOrNull$lambda(closure$meth, closure$result) {
    return function (it) {
      if (closure$meth(it)) {
        closure$result.v = it;
        return false;
      } else
        return null;
    };
  }
  Token.prototype.queryOrNullAtDepth = function (depth, meth) {
    if (depth === void 0)
      depth = null;
    var result = {v: null};
    this.walkAtDepth(depth, Token$queryOrNull$lambda(meth, result));
    return result.v;
  };
  Token.prototype.queryOrNull = function (meth) {
    return this.queryOrNullAtDepth(null, meth);
  };
  function Token$queryAny$lambda(closure$meth, closure$result) {
    return function (it) {
      if (closure$meth(it)) {
        closure$result.add_11rb$(it);
        return true;
      } else
        return null;
    };
  }
  Token.prototype.queryAnyAtDepth = function (depth, meth) {
    if (depth === void 0)
      depth = null;
    var result = ArrayList_init_0();
    this.walkAtDepth(depth, Token$queryAny$lambda(meth, result));
    return copyToArray(result);
  };
  Token.prototype.queryAny = function (meth) {
    return this.queryAnyAtDepth(null, meth);
  };
  Token.prototype.queryAtDepth = function (depth, meth) {
    if (depth === void 0)
      depth = null;
    var tmp$;
    tmp$ = this.queryOrNullAtDepth(depth, meth);
    if (tmp$ == null) {
      throw new QueryFailedError(this);
    }
    return tmp$;
  };
  Token.prototype.query = function (meth) {
    return this.queryAtDepth(null, meth);
  };
  Token.prototype.queryManyAtDepth = function (depth, meth) {
    if (depth === void 0)
      depth = null;
    var result = this.queryAnyAtDepth(depth, meth);
    if (result.length === 0)
      throw new QueryFailedError(this);
    return result;
  };
  Token.prototype.queryMany = function (meth) {
    return this.queryManyAtDepth(null, meth);
  };
  Token.prototype.toString = function () {
    var result = '(' + this.index + ';' + this.value + ') ' + this.meta;
    if (this.children.length === 0)
      return result;
    result += replace('\n' + joinToString_0(this.children, '\n'), '\n', '\n\t');
    return result;
  };
  Token.$metadata$ = {kind: Kind_CLASS, simpleName: 'Token', interfaces: []};
  Token.prototype.component1 = function () {
    return this.source;
  };
  Token.prototype.component2 = function () {
    return this.index;
  };
  Token.prototype.component3 = function () {
    return this.value;
  };
  Token.prototype.copy_ui6i3r$ = function (source, index, value) {
    return new Token(source === void 0 ? this.source : source, index === void 0 ? this.index : index, value === void 0 ? this.value : value);
  };
  Token.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.source) | 0;
    result = result * 31 + Kotlin.hashCode(this.index) | 0;
    result = result * 31 + Kotlin.hashCode(this.value) | 0;
    return result;
  };
  Token.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.source, other.source) && Kotlin.equals(this.index, other.index) && Kotlin.equals(this.value, other.value)))));
  };
  Object.defineProperty(Cursor, 'Companion', {get: Cursor$Companion_getInstance});
  var package$dev = _.dev || (_.dev = {});
  var package$nickmatt = package$dev.nickmatt || (package$dev.nickmatt = {});
  var package$parseknife = package$nickmatt.parseknife || (package$nickmatt.parseknife = {});
  package$parseknife.Cursor = Cursor;
  package$parseknife.main = main;
  package$parseknife.OrphanedTokenError = OrphanedTokenError;
  package$parseknife.ParseKnifeError = ParseKnifeError;
  Object.defineProperty(ParseKnifeError, 'Companion', {get: ParseKnifeError$Companion_getInstance});
  package$parseknife.ParseKnifeError_init_fepdqp$ = ParseKnifeError_init;
  package$parseknife.ParseKnifeError_init_4d5z35$ = ParseKnifeError_init_1;
  package$parseknife.ParseKnifeError_init_qx0dmb$ = ParseKnifeError_init_2;
  package$parseknife.ParseKnifeError_init_dn5v1i$ = ParseKnifeError_init_4;
  package$parseknife.ParseKnifeError_init_g59ico$ = ParseKnifeError_init_5;
  package$parseknife.QueryFailedError = QueryFailedError;
  var package$rule = package$parseknife.rule || (package$parseknife.rule = {});
  package$rule.AndRule = AndRule;
  package$rule.AnyRule = AnyRule;
  Object.defineProperty(CharacterRule, 'Companion', {get: CharacterRule$Companion_getInstance});
  package$rule.CharacterRule = CharacterRule;
  Object.defineProperty(EofRule, 'Companion', {get: EofRule$Companion_getInstance});
  package$rule.EofRule = EofRule;
  package$rule.ManyRule = ManyRule;
  package$rule.MaybeRule = MaybeRule;
  package$rule.NotRule = NotRule;
  package$rule.OrRule = OrRule;
  Object.defineProperty(RegexRule, 'Companion', {get: RegexRule$Companion_getInstance});
  package$rule.RegexRule = RegexRule;
  package$rule.Rule = Rule;
  Object.defineProperty(Rule, 'Companion', {get: Rule$Companion_getInstance});
  Object.defineProperty(RuleHelper, 'Companion', {get: RuleHelper$Companion_getInstance});
  package$rule.RuleHelper = RuleHelper;
  package$rule.RuleInferenceError = RuleInferenceError;
  package$parseknife.Source = Source;
  package$parseknife.SourceLocation = SourceLocation;
  Object.defineProperty(Token, 'Companion', {get: Token$Companion_getInstance});
  package$parseknife.Token = Token;
  r = RuleHelper$Companion_getInstance().instance;
  eofTerm = r.invoke_jiburq$(['$', 'e', 'o', 'f']).withMeta_bm4g0d$('ruleName', 'endOfFile');
  integer = r.regex('[0-9]+').withMeta_bm4g0d$('ruleName', 'integer');
  character = r.regex("'(?<content>\\\\.|[^'])'").withMeta_bm4g0d$('ruleName', 'character');
  string = r.regex('"(?<content>(\\\\.|[^"])*)"').withMeta_bm4g0d$('ruleName', 'string');
  regex = r.regex('/(?<content>(\\\\.|[^/])*)/').withMeta_bm4g0d$('ruleName', 'regex');
  group = r.invoke_jiburq$(['(', rExpression_getInstance(), ')']).withMeta_bm4g0d$('ruleName', 'group');
  ruleName = r.regex('[a-zA-Z_]+').withMeta_bm4g0d$('ruleName', 'ruleName');
  termValue = r.or_jiburq$([eofTerm, integer, character, string, regex, ruleName, group]).withMeta_bm4g0d$('ruleName', 'termValue');
  decorator = r.many(r.or_jiburq$(['+', '*', '?', '!', '^']));
  term = (new AndRule([termValue, r.maybe(decorator).withMeta_bm4g0d$('ruleName', 'decorator')])).withWhitespaceSensitivity().withMeta_bm4g0d$('ruleName', 'term');
  var and = (new AndRule([term, r.maybe(r.many((new AndRule([RegexRule$Companion_getInstance().WHITESPACE, term])).withWhitespaceSensitivity()))])).withWhitespaceSensitivity().withMeta_bm4g0d$('ruleName', 'and');
  _expression = (new AndRule([and, r.maybe(r.many(r.invoke_jiburq$(['|', and])))])).withWhitespaceSensitivity().withMeta_bm4g0d$('ruleName', 'or');
  language = r.invoke_jiburq$([r.many(r.invoke_jiburq$([ruleName, '=', rExpression_getInstance(), ';']).withMeta_bm4g0d$('ruleName', 'rule')).withMeta_bm4g0d$('ruleName', 'language'), r.eof().withMeta_bm4g0d$('ruleName', 'endOfFile')]).withMeta_bm4g0d$('ruleName', 'file');
  r_0 = RuleHelper$Companion_getInstance().instance;
  r_1 = RuleHelper$Companion_getInstance().instance;
  REGEX_ESCAPE_REGEX = Regex_init('\\\\.');
  ESCAPE_REGEX = Regex_init('\\\\(?<code>[tbnr\'"\\\\])');
  ESCAPE_CODES = mapOf([new Pair('\\t', toBoxedChar(9)), new Pair('\\b', toBoxedChar(8)), new Pair('\\n', toBoxedChar(10)), new Pair('\\r', toBoxedChar(13)), new Pair("\\'", toBoxedChar(39)), new Pair('\\"', toBoxedChar(34)), new Pair('\\\\', toBoxedChar(92))]);
  main();
  return _;
}));

//# sourceMappingURL=parseknife.js.map

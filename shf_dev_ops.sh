#!/bin/sh

[ -f "$HOME"/.profile ] && . "$HOME"/.profile >/dev/null
[ -f "$HOME"/.zprofile ] && . "$HOME"/.zprofile
[ -f "$HOME"/.zshrc ] && . "$HOME"/.zshrc

__INCLUDE_COUNT__=${__INCLUDE_COUNT__:-0}
__INCLUDE_COUNT__=$((__INCLUDE_COUNT__ + 1))
export __INCLUDE_COUNT__


track_exit_code() {
	exitCode="$?"
	if [ -z "$fnExitCode" ]; then
		fnExitCode="$exitCode"
	fi
	((exit "$fnExitCode") || (exit "$exitCode"))
	fnExitCode="$?"
}


install_package() (
	pkgName="$1"
	echo "Try to install --${pkgName}--"
	case $(uname) in
		(Linux*)
			if which pacman >/dev/null 2>&1; then
				yes | sudo -p 'Pass required for pacman install: ' \
					pacman -S "$pkgName"
			elif which apt-get >/dev/null 2>&1; then
				sudo -p 'Pass required for apt-get install: ' \
					DEBIAN_FRONTEND=noninteractive apt-get -y install "$pkgName"
			fi
			;;
		(Darwin*)
			yes | brew install "$pkgName"
			;;
		(*)
			;;
	esac
)


#geared for having a bunch of values piped to it
input_match() (
	matchFor="$1"
	while read nextValue; do
		[ "$nextValue" = "$matchFor" ] && echo 't'
	done
)


enable_wordsplitting() {
	if [ -n "$ZSH_VERSION" ]; then
		setopt shwordsplit
	fi
}


disable_wordsplitting() {
	if [ -n "$ZSH_VERSION" ]; then
		unsetopt shwordsplit
	fi
}


str_contains() (
	haystackStr="$1"
	needleStr="$2"
	case "$haystackStr" in
		(*"$needleStr"*)
			return 0
	esac
	return 1
)


#the array needs to be passed in unquoted.
#example
# array_contains "$findMe" $arrayOfSpaceSeparatedWords
array_contains() (
	searchValue="$1"
	shift
	while [ ! -z "$1" ]; do
		case $1 in
			("$searchValue")
				return 0
				;;
			(*)
			;;
		esac
		shift
	done
	return 1
)


is_dir_empty() (
	targetDir="$1"
	lsRes=$(ls -A $targetDir)
	[ ! -d "$targetDir" ] || [ -z "$lsRes" ]
)


error_check_path() (
	targetDir="$1"
	if echo "$targetDir" | grep '\/\/'; then
		echo "segments seem to be missing in '${targetDir}'"
		return 1
	elif [ "$targetDir" = '/' ];then
		echo "segments seem to be missing in '${targetDir}'"
		return 1
	fi
)


error_check_all_paths() (

	while [ ! -z "$1" ]; do
		error_check_path "$1" || return "$?"
		shift
	done

)


sudo_rm_contents() (
	dirEmptira="$1"
	if [ -w "$dirEmptira" ]; then
		rm -rf "$dirEmptira"/*
	else
		sudo -p "Password required to remove files from ${dirEmptira}: " \
			rm -rf "$dirEmptira"/*
	fi
)


rm_contents_if_exist() (
	dirEmptira="$1"
	if ! is_dir_empty "$dirEmptira"; then
		sudo_rm_contents "$dirEmptira"
	fi
)


sudo_rm_dir() (
	dirEmptira="$1"
	if [ -w "$dirEmptira" ]; then
		rm -rf "$dirEmptira"
	else
		sudo -p "Password required to remove ${dirEmptira}: " \
			rm -rf "$dirEmptira"
	fi
)


sudo_cp_contents() (
	fromDir="$1"
	toDir="$2"
	if [ -r "$fromDir" ] && [ -w "$toDir" ]; then
		cp -rv "$fromDir"/. "$toDir"
	else
		sudo -p 'Pass required to copy files: ' \
			cp -rv "$fromDir"/. "$toDir"
	fi
)


sudo_mkdir() (
	dirMakera="$1"
	mkdir -pv "$dirMakera" ||
	sudo -p "Password required to create ${dirMakera}: " \
		mkdir -pv "$dirMakera"
)


empty_dir_contents() (
	dirEmptira="$1"
	echo "emptying '${dirEmptira}'"
	error_check_path "$dirEmptira" &&
	if [ -e "$dirEmptira" ]; then
		rm_contents_if_exist || return "$?"
	else
		sudo_mkdir "$dirEmptira" || return "$?"
	fi &&
	echo "done emptying '${dirEmptira}'"
)


get_bin_path() (
	pkg="$1"
	case $(uname) in
		(Darwin*)
			brew info "$pkg" \
			| grep -A1 'has been installed as' \
			| awk 'END{ print $1 }'
			;;
		(*) which "$pkg" ;;
	esac
)


#this needs to command group and not a subshell
#else it will basically do nothing
show_err_and_exit() {
	errCode="$?"
	msg="$1"
	[ ! -z "$msg" ] && echo "$msg"
	exit "$errCode"
}


show_err_and_return() {
	errCode="$?"
	msg="$1"
	[ ! -z "$msg" ] && echo "$msg"
	return "$errCode"
}


#needed this method because perl will still
#exit 0 even if a file doesn't exist
does_file_exist() (
	candidate="$1"
	if [ ! -e "$candidate" ]; then
		echo "${candidate} does not exist"
		return 1
	fi
)


unroot_dir() (
	dirUnrootura="$1"
	if [ ! -w "$dirUnrootura" ]; then
		prompt='Password required to change owner of'
		prompt="${prompt} ${dirUnrootura} to current user: "
		sudo -p "$prompt" \
			chown -R "$SHF_CURRENT_USER": "$dirUnrootura"
	fi
)


gen_pass() (
	pass_len=${1:-16}
	LC_ALL=C tr -dc 'A-Za-z0-9' </dev/urandom | head -c "$pass_len"
)


is_ssh() (
	[ -n "$SSH_CONNECTION" ]
)


compare_dirs() (
	srcDir="$1"
	cpyDir="$2"
	error_check_all_paths "$srcDir" "$cpyDir"
	exitCode=0
	if [ ! -e "$cpyDir" ]; then
		echo "$cpyDir/ is not in place"
		return 1
	fi
	srcFifo='src_fifo'
	cpyFifo='cpy_fifo'
	cmpFifo='cmp_fifo'
	rm -f "$srcFifo" "$cpyFifo" "$cmpFifo"
	mkfifo "$srcFifo" "$cpyFifo" "$cmpFifo"

	srcRes=$(find "$srcDir" | \
		sed "s@${srcDir%/}/\{0,1\}@@" | sort)
	cpyRes=$(find "${cpyDir}" -not -path "${cpyDir}/${SHF_PY_ENV}/*" \
		-and -not -path "${cpyDir}/${SHF_PY_ENV}" | \
		sed "s@${cpyDir%/}/\{0,1\}@@" | sort)

	get_file_list() (
		supress="$1"
		echo "$srcRes" > "$srcFifo" &
		echo "$cpyRes" > "$cpyFifo" &
		[ -n "$supress" ] && comm "-${supress}" "$srcFifo" "$cpyFifo" ||
			comm "$srcFifo" "$cpyFifo"
	)

	inBoth=$(get_file_list 12)
	inSrc=$(get_file_list 23)
	inCpy=$(get_file_list 13)
	[ -n "$(echo "${inCpy}" | xargs)" ] &&
			{
				echo "There are items that only exist in ${cpyDir}"
				exitCode=2
			}
	[ -n "$(echo "${inSrc}" | xargs)" ] &&
			{
				echo "There are items missing from the ${cpyDir}"
				exitCode=3
			}
	if [ -n "$inBoth" ]; then
		exitCode=4
		echo "$inBoth" > "$cmpFifo" &
		while read fileName; do
			[ "${srcDir%/}/${fileName}" -nt "${cpyDir%/}/${fileName}" ] &&
				echo "${fileName} is outdated"
		done <"$cmpFifo"
	fi
	rm -f "$srcFifo" "$cpyFifo" "$cmpFifo"
	return "$exitCode"
)


is_newer_than_files() (
	candidate="$1"
	dir_to_check="$2"
	find "$dir_to_check" -newer "$candidate"
)


literal_to_regex() (
	#this will handle cases as I need them and not exhaustively
	str="$1"
	echo "$str" | sed 's/\*/\\*/g'
)


__get_keychain_osx__() (
	echo '/Library/Keychains/System.keychain'
)


copy_dir() (
	fromDir="$1"
	toDir="$2"
	echo "copying from ${fromDir} to ${toDir}"
	error_check_all_paths "$fromDir"/. "$toDir" &&
	empty_dir_contents "$toDir" &&
	sudo_cp_contents "$fromDir" "$toDir" &&
	unroot_dir "$toDir" &&
	echo "done copying dir from ${fromDir} to ${toDir}"
)


set_env_vars() {
	process_global_vars "$@" &&
	__set_env_path_var__
}


get_localhost_key_dir() (
	case $(uname) in
		(Darwin*)
			echo "$HOME"/.ssh
			;;
		(Linux*)
			echo "$HOME"/.ssh
			;;
		(*) ;;
	esac
)


get_ssl_vars() (
	process_global_vars "$@" >&2 &&
	sendJson=$(cat <<-END
	{
		"secretapikey": "$(__get_pb_secret__)",
		"apikey": "$(__get_pb_api_key__)"
	}
	END
	) &&
	curl -s --header "Content-Type: application/json" \
	--request POST \
	--data "$sendJson" \
	https://porkbun.com/api/json/v3/ssl/retrieve/$(__get_domain_name__)

)


stdin_json_extract_value() (
	jsonKey="$1"
	python3 -c \
	"import sys, json; print(json.load(sys.stdin, strict=False)['$jsonKey'])"
)


stdin_json_top_level_keys() (
	python3 -c \
	"import sys, json; print(json.load(sys.stdin, strict=False).keys())"
)


#other keys: 'intermediatecertificate', 'certificatechain'
get_ssl_private() (
	process_global_vars "$@" >&2 &&
	get_ssl_vars | stdin_json_extract_value 'privatekey'
)


get_ssl_public() (
	process_global_vars "$@" >&2 &&
	get_ssl_vars | stdin_json_extract_value 'publickey'
)


set_python_version_const() {
	#python version info
	if shf-python -V >/dev/null 2>&1; then
		pyVersion=$(shf-python -V)
	elif python3 -V >/dev/null 2>&1; then
		pyVersion=$(python3 -V)
	elif python -V >/dev/null 2>&1; then
		pyVersion=$(python -V)
	else
		return 1
	fi
	pyMajor=$(echo "$pyVersion" | perl -ne 'print "$1\n" if /(\d+)\.\d+/')
	pyMinor=$(echo "$pyVersion" | perl -ne 'print "$1\n" if /\d+\.(\d+)/')
}


is_python_version_good() {
	[ "$__EXPERIMENT_NAME__" = 'py3.8' ] && return 0
	set_python_version_const &&
	[ "$pyMajor" -eq 3 ] && [ "$pyMinor" -ge 9 ]
}


__is_current_dir_repo__() {
	dir="$1"
	[ -f "$dir"/dev_ops.sh ] &&
	[ -f "$dir"/README.md ] &&
	[ -f "$dir"/deploy_to_server.sh ] &&
	[ -d "$dir"/.vscode ] &&
	[ -d "$dir"/src ] &&
	[ -d "$dir"/src/space_habit_frontier ]
}


get_pkg_mgr() {
	define_consts >&2
	case $(uname) in
		(Linux*)
			if  which pacman >/dev/null 2>&1; then
				echo "$SHF_PACMAN"
				return 0
			elif which apt-get >/dev/null 2>&1; then
				echo "$SHF_APT_CONST"
				return 0
			fi
			;;
		(Darwin*)
			echo "$SHF_HOMEBREW"
			return 0
			;;
		(*)
			;;
	esac
	return 1
}


brew_is_installed() (
	pkg="$1"
	echo "checking about $pkg"
	case $(uname) in
		(Darwin*)
			brew info "$pkg" >/dev/null 2>&1 &&
			! brew info "$pkg" | grep 'Not installed' >/dev/null
			;;
		(*) return 0 ;;
	esac
)


__deployment_env_check_recommended__() {
	#possibly problems if missing

	[ -z "$SHF_LOCAL_REPO_DIR" ] &&
	echo 'environmental var SHF_LOCAL_REPO_DIR not set'
	[ -n "$(__get_db_owner_key__)" ] ||
	echo 'deployment var SHF_DB_PASS_OWNER not set in keys'
}


__deployment_env_check_required__() {
	#definitely problems if missing
	[ -z "$SHF_REPO_URL" ] &&
	echo 'environmental var SHF_REPO_URL not set'
	fnExitCode="$?"
	track_exit_code
	[ -z $(__get_domain_name__) ] &&
	echo 'top level domain for app has not been set. Check __get_domain_name__'
	track_exit_code

	#values for ssh'ing to server
	[ -z $(__get_id_file__) ] &&
	echo 'deployment var SHF_SERVER_KEY_FILE not set in keys'
	track_exit_code
	[ -z $(__get_address__) ] &&
	echo 'deployment var SHF_SERVER_SSH_ADDRESS not set in keys'
	track_exit_code

	#porkbun
	[ -z $(__get_pb_api_key__) ] &&
	echo 'deployment var PB_API_KEY not set in keys'
	track_exit_code
	[ -z $(__get_pb_secret__) ] &&
	echo 'deployment var PB_SECRET not set in keys'
	track_exit_code

	#for encrypting app token
	[ -z $(__get_shf_auth_key__) ] &&
	echo 'deployment var SHF_AUTH_SECRET_KEY not set in keys'
	track_exit_code

}


deployment_env_check() (
	echo 'checking environment vars before deployment'
	__deployment_env_check_recommended__
	__deployment_env_check_required__
)


__server_env_check_recommended__() {
	#possibly problems if missing
	[ -z "$SHF_LOCAL_REPO_DIR" ] &&
	echo 'environmental var SHF_LOCAL_REPO_DIR not set'
}


__server_env_check_required__() {
	#definitely problems if missing
	[ -z "$SHF_REPO_URL" ] &&
	echo 'environmental var SHF_REPO_URL not set'
	fnExitCode="$?"
	track_exit_code

	[ -z $(__get_domain_name__) ] &&
	echo 'top level domain for app has not been set. Check __get_domain_name__'
	track_exit_code

	#porkbun
	[ -z "$PB_API_KEY" ] &&
	echo 'environmental var PB_API_KEY not set'
	track_exit_code
	[ -z "$PB_SECRET" ] &&
	echo 'environmental var PB_SECRET not set'
	track_exit_code

	#for encrypting app token
	[ -z "$SHF_AUTH_SECRET_KEY" ] &&
	echo 'environmental var SHF_AUTH_SECRET_KEY not set'
	track_exit_code

	return "$fnExitCode"
}

server_env_check() (
	echo 'checking environment vars on server'
	__server_env_check_recommended__
	__server_env_check_required__
)


__dev_env_check_recommended__() {
	#possibly problems if missing
	[ -z "$BOT_REPO_URL" ] &&
	echo 'environmental var BOT_REPO_URL not set'
}


__dev_env_check_required__() {
	#definitely problems if missing
	[ -z "$SHF_LOCAL_REPO_DIR" ] &&
	echo 'environmental var SHF_LOCAL_REPO_DIR not set'
	fnExitCode="$?"
	track_exit_code

	[ -z $(__get_domain_name__) ] &&
	echo 'top level domain for app has not been set. Check __get_domain_name__'
	track_exit_code

	[ -z "$SHF_ENV" ] &&
	echo 'environmental var SHF_ENV not set'
	fnExitCode="$?"
	track_exit_code

	#for encrypting app token
	[ -z "$SHF_AUTH_SECRET_KEY" ] &&
	echo 'environmental var SHF_AUTH_SECRET_KEY not set'
	track_exit_code

	return "$fnExitCode"
}


dev_env_check() (
	echo 'checking environment vars on local dev environment'
	__dev_env_check_recommended__
	__dev_env_check_required__
)


get_repo_path() (
	if [ -n "$SHF_LOCAL_REPO_DIR" ]; then
		echo "$SHF_LOCAL_REPO_DIR"
		return
	elif __is_current_dir_repo__ "$PWD"; then
		echo "$PWD"
		return
	else
		for guess in \
			$(find "$HOME" -maxdepth 5 -type d \
				-path "$SHF_BUILD_DIR"/"$SHF_PROJ_NAME_SNAKE"
				);
		do
			if __is_current_dir_repo__ "$guess"; then
				echo "$guess"
				return
			fi
		done
	fi
	#done't try to change from home
	#fallback
	echo "$HOME"/"$SHF_BUILD_DIR"/"$SHF_PROJ_NAME_SNAKE"
)

generate_initial_keys_file() (
	process_global_vars "$@" &&
	if [ ! -s  "$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE" ]; then
		keyFile="$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
		nsUuid="$(python3 -c 'import uuid; print(uuid.uuid4())')"
		echo "PB_SECRET=" > "$keyFile"
		echo "PB_API_KEY=" >> "$keyFile"
		echo "SHF_AUTH_SECRET_KEY=$(openssl rand -hex 32)" >> "$keyFile"
		echo "SHF_SERVER_SSH_ADDRESS=root@" >> "$keyFile"
		echo "SHF_SERVER_KEY_FILE=" >> "$keyFile"
		echo "SHF_DB_PASS_API=$(openssl rand -hex 16)" >> "$keyFile"
		echo "SHF_DB_PASS_OWNER=$(openssl rand -hex 16)" >> "$keyFile"
		echo "__DB_SETUP_PASS__=$(openssl rand -hex 16)" >> "$keyFile"
		echo "SHF_NAMESPACE_UUID=$nsUuid" >> "$keyFile"
	fi
)

__set_env_path_var__() {
	if perl -e "exit 1 if index('$PATH','$(__get_app_root__)/${SHF_BIN_DIR}') != -1";
	then
		echo "Please add '$(__get_app_root__)/${SHF_BIN_DIR}' to path"
		export PATH="$PATH":"$(__get_app_root__)"/"$SHF_BIN_DIR"
	fi
}


__get_pb_api_key__() (
	if [ -n "$PB_API_KEY" ] && [ "$SHF_ENV" != 'local' ]; then
		echo "$PB_API_KEY"
		return
	fi
	perl -ne 'print "$1\n" if /PB_API_KEY=(\w+)/' \
		"$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
)


__get_pb_secret__() (
	if [ -n "$PB_SECRET" ] && [ "$SHF_ENV" != 'local' ]; then
		echo "$PB_SECRET"
		return
	fi
	perl -ne 'print "$1\n" if /PB_SECRET=(\w+)/' \
		"$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
)


__get_shf_auth_key__() (
	if [ -n "$SHF_AUTH_SECRET_KEY" ] && [ "$SHF_ENV" != 'local' ]; then
		echo "$SHF_AUTH_SECRET_KEY"
		return
	fi
	perl -ne 'print "$1\n" if /SHF_AUTH_SECRET_KEY=(\w+)/' \
		"$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
)


__get_address__() (
	if [ -n "$SHF_SERVER_SSH_ADDRESS" ]; then
		echo "$SHF_SERVER_SSH_ADDRESS"
		return
	fi
	keyFile="$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
	perl -ne 'print "$1\n" if /SHF_SERVER_SSH_ADDRESS=root@([\w:]+)/' "$keyFile"
)


__get_id_file__() (
	if [ -n "$SHF_SERVER_KEY_FILE" ]; then
		echo "$SHF_SERVER_KEY_FILE"
		return
	fi
	keyFile="$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
	perl -ne 'print "$1\n" if /SHF_SERVER_KEY_FILE=(.+)/' "$keyFile"
)

__get_db_owner_key__() (
	if [ -n "$SHF_DB_PASS_OWNER" ] && [ "$SHF_ENV" != 'local' ]; then
		echo "$SHF_DB_PASS_OWNER"
		return
	fi
	perl -ne 'print "$1\n" if /SHF_DB_PASS_OWNER=(\w+)/' \
		"$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
)

__get_api_db_user_key__() (
	if [ -n "$SHF_DB_PASS_API" ] && [ "$SHF_ENV" != 'local' ]; then
		echo "$SHF_DB_PASS_API"
		return
	fi
	perl -ne 'print "$1\n" if /SHF_DB_PASS_API=(\w+)/' \
		"$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
)


__get_remote_private_key__() (
	echo "/etc/ssl/private/${SHF_PROJ_NAME_0}.private.key.pem"
)


__get_remote_public_key__() (
	echo "/etc/ssl/certs/${SHF_PROJ_NAME_0}.public.key.pem"
)


__get_remote_intermediate_key__() (
	echo "/etc/ssl/certs/${SHF_PROJ_NAME_0}.intermediate.key.pem"
)


replace_lib_files() (
	process_global_vars "$@" &&
	__replace_lib_files__
)


# set up the python environment, then copy
# subshell () auto switches in use python version back at the end of function
create_py_env_in_dir() (
	echo "setting up py libs"
	__set_env_path_var__ #ensure that we can see shf-python
	link_app_python_if_not_linked
	set_python_version_const || return "$?"
	env_root=${1:-"$(__get_app_root__)"/"$SHF_TRUNK"}
	pyEnvDir="$env_root"/"$SHF_PY_ENV"
	error_check_path "$pyEnvDir" &&
	shf-python -m virtualenv "$pyEnvDir" &&
	. "$pyEnvDir"/bin/activate &&
	#this is to make some of my newer than checks work
	touch "$pyEnvDir" &&
	# #python_env
	# use regular python command rather shf-python
	# because shf-python still points to the homebrew location
	python -m pip install -r "$(__get_app_root__)"/"$SHF_TRUNK"/requirements.txt &&
	echo "done setting up py libs"
)


create_py_env_in_app_trunk() (
	process_global_vars "$@" &&
	sync_requirement_list &&
	create_py_env_in_dir &&
	__replace_lib_files__
)


get_libs_dest_dir() (
	__set_env_path_var__ >&2 #ensure that we can see shf-python
	set_python_version_const || return "$?"
	envRoot="$1"
	packagePath="${SHF_PY_ENV}/lib/python${pyMajor}.${pyMinor}/site-packages/"
	echo "$envRoot"/"$packagePath"
)


__install_py_env__() {
	sync_requirement_list &&
	create_py_env_in_app_trunk
}


install_py_env() {
	unset_globals
	process_global_vars "$@" &&
	__install_py_env__ &&
	echo "done installing py env"
}


__replace_lib_files__() {
	envRoot="$(__get_app_root__)"/"$SHF_TRUNK"
	copy_dir "$SHF_DEV_OPS_LIB_SRC" \
		"$(get_libs_dest_dir "$envRoot")""$SHF_DEV_OPS_LIB"
}


__install_py_env_if_needed__() {
	if [ ! -e "$(__get_app_root__)"/"$SHF_TRUNK"/"$SHF_PY_ENV"/bin/activate ]; then
		__install_py_env__
	else
		echo "replacing space_habit_frontier files"
		__replace_lib_files__ >/dev/null #only replace my code
	fi
}

activate_shf_env() {
	if [ -n "$VIRTUAL_ENV" ]; then
		deactivate 2>&1 1>/dev/null
	fi
	set_env_vars "$@" &&
	__install_py_env_if_needed__ &&
	. "$(__get_app_root__)"/"$SHF_TRUNK"/"$SHF_PY_ENV"/bin/activate
}


start_python() (
	activate_shf_env &&
	python
)


link_app_python_if_not_linked() {
	if ! shf-python -V 2>/dev/null; then
		if [ ! -e "$(__get_app_root__)"/"$SHF_BIN_DIR" ]; then
			sudo_mkdir "$(__get_app_root__)"/"$SHF_BIN_DIR" || return "$?"
		fi
		case $(uname) in
			(Darwin*)
				ln -sf $(get_bin_path python@3.9) \
					"$(__get_app_root__)"/"$SHF_BIN_DIR"/shf-python
				;;
			(*)
				ln -sf $(get_bin_path python3) \
					"$(__get_app_root__)"/"$SHF_BIN_DIR"/shf-python
				;;
		esac
	fi
	echo "done linking"
}




#test runner needs to read .env
setup_env_api_file() (
	echo 'setting up .env file'
	envFile="$(__get_app_root__)"/"$SHF_CONFIG_DIR"/.env
	error_check_all_paths "$SHF_TEMPLATES_SRC"/.env_api "$envFile" &&
	pkgMgrChoice=$(get_pkg_mgr) &&
	cp "$SHF_TEMPLATES_SRC"/.env_api "$envFile" &&
	does_file_exist "$envFile" &&
	perl -pi -e \
		"s@^(SHF_CONTENT_DIR=).*\$@\1'${SHF_CONTENT_DIR}'@" \
		"$envFile" &&
	perl -pi -e \
		"s@^(SHF_TEMPLATES_DEST=).*\$@\1'${SHF_TEMPLATES_DEST}'@" \
		"$envFile" &&
	perl -pi -e \
		"s@^(SHF_SQL_SCRIPTS_DEST=).*\$@\1'${SHF_SQL_SCRIPTS_DEST}'@" \
		"$envFile" &&
	perl -pi -e \
		"s@^(SHF_TEST_ROOT=).*\$@\1'${SHF_TEST_ROOT}'@" \
		"$envFile" &&
	echo 'done setting up .env file'
)


__setup_db_postgresql__() (
	process_global_vars "$@" &&
	dbName="space_habit_frontier_db" &&
	if ! psql --list | grep "$dbName" >/dev/null; then
		createdb "$dbName" &&
		psql -d "$dbName" -c \
			"CREATE USER shf_db_mgr WITH PASSWORD '${SHF_DB_PASS_OWNER}'" &&
		psql -d "$dbName" -c \
			"CREATE USER shf_api_user WITH PASSWORD '${SHF_DB_PASS_API}'" &&
		psql -d "$dbName" -c \
			"GRANT ALL PRIVILEGES ON DATABASE $dbName TO shf_db_mgr WITH GRANT OPTION"
	fi
)

setup_db() (
	__setup_db_postgresql__
)

sync_utility_scripts() (
	process_global_vars "$@" &&
	cp "$(get_repo_path)"/shf_dev_ops.sh "$(__get_app_root__)"/shf_dev_ops.sh
)


#copy python dependency file to the deployment directory
sync_requirement_list() (
	process_global_vars "$@" &&
	error_check_all_paths "$(get_repo_path)"/requirements.txt \
		"$(__get_app_root__)"/"$SHF_TRUNK"/requirements.txt \
		"$(__get_app_root__)"/requirements.txt &&
	#keep a copy in the parent radio directory
	cp "$(get_repo_path)"/requirements.txt \
		"$(__get_app_root__)"/"$SHF_TRUNK"/requirements.txt &&
	cp "$(get_repo_path)"/requirements.txt "$(__get_app_root__)"/requirements.txt
)


run_initial_install() (
	process_global_vars "$@" &&
	__setup_api_dir__
	sh $(get_repo_path)/install.sh
)


__get_debug_cert_name__() (
	echo "$SHF_PROJ_NAME_SNAKE"_localhost_debug
)


__get_debug_cert_path__() (
	echo $(get_localhost_key_dir)/$(__get_debug_cert_name__)
)


__get_local_nginx_cert_name__() (
	echo "$SHF_PROJ_NAME_SNAKE"_localhost_nginx
)


__get_local_nginx_cert_path__() (
	echo $(get_localhost_key_dir)/$(__get_local_nginx_cert_name__)
)


is_cert_expired() (
	! openssl x509 -checkend 3600 -noout >/dev/null
)


extract_sha256_from_cert() (
	openssl x509 -fingerprint -sha256 \
	| perl -ne 'print "$1\n" if /SHA256 Fingerprint=([A-F0-9:]+)/' | tr -d ':'
)


extract_commonName_from_cert() (
	openssl x509 -subject \
	| perl -ne 'print "$1\n" if m{CN *= *([^/]+)}'
)


scan_pems_for_common_name() (
	commonName="$1"
	activate_shf_env &&
	python -m 'space_habit_frontier_dev_ops.installed_certs' "$commonName" \
		< /etc/ssl/certs/ca-certificates.crt
)


certs_matching_name() (
	commonName="$1"
		case $(uname) in
		(Darwin*)
			security find-certificate -a -p -c "$commonName" \
				$(__get_keychain_osx__)
			;;
		(*)
			scan_pems_for_common_name "$commonName"
			;;
	esac
)


__certs_matching_name_exact__() (
	commonName="$1"
	certs_matching_name "$commonName" \
	| extract_commonName_from_cert \
	| input_match "$commonName"
)


__get_openssl_default_conf__() (
	case $(uname) in
		(Darwin*)
			echo '/System/Library/OpenSSL/openssl.cnf'
			;;
		(Linux*)
			echo '/etc/ssl/openssl.cnf'
			;;
		(*) ;;
	esac
)


__openssl_gen_cert__() (
	commonName="$1"
	domain="$2" &&
	publicKeyFile="$3" &&
	privateKeyFile="$4" &&
	mkfifo cat_config_fifo
	{
	cat<<-OpenSSLConfig
	$(cat $(__get_openssl_default_conf__))
	$(printf "[SAN]\nsubjectAltName=DNS:${domain},IP:127.0.0.1")
	OpenSSLConfig
	} > cat_config_fifo &
	openssl req -x509 -sha256 -new -nodes -newkey rsa:2048 -days 7 \
	-subj "/C=US/ST=CA/O=fake/CN=${commonName}" -reqexts SAN -extensions SAN \
	-config cat_config_fifo \
	-keyout "$privateKeyFile" -out "$publicKeyFile"
	errCode="$?"
	rm -f cat_config_fifo
	return "$errCode"
)


__install_local_cert_osx__() (
	publicKeyFile="$1" &&
	sudo security add-trusted-cert -p \
		ssl -d -r trustRoot \
		-k $(__get_keychain_osx__) "$publicKeyFile"
)


create_firefox_cert_policy_file() (
	publicKeyName="$1" &&
	pemFile=$(echo "$publicKeyName" | sed 's/.crt$/.pem/')
	content=$(cat <<END
{
	"policies": {
		"Certificates": {
			"ImportEnterpriseRoots": true,
			"Install": [
				"$publicKeyName",
				"/etc/ssl/certs/$pemFile"
			]
		}
	}
}
END
)
	sudo -p "Need password to create firefox policy file" \
		sh -c \
		"echo '$content' > '/usr/share/firefox-esr/distribution/policies.json'"
)


__set_firefox_cert_policy__() (
	publicKeyName="$1" &&
	policyFile='/usr/share/firefox-esr/distribution/policies.json'
	if firefox -v 2>/dev/null; then
		if [ -s "$policyFile" ]; then
			content=$(cat "$policyFile" \
			| get_trusted_by_firefox_json_with_added_cert "$publicKeyName")
				if (exit "$?"); then
					sudo -p "Need password to update firefox policy file" \
					sh -c \
					"echo '$content' > '$policyFile'"
				else
					create_firefox_cert_policy_file "$publicKeyName"
				fi
		else
			create_firefox_cert_policy_file "$publicKeyName"
		fi
	fi
)


get_trusted_by_firefox_json_with_added_cert() (
	publicKeyFile="$1"
	pemFile=$(echo "$publicKeyFile" | sed 's/.crt$/.pem/')
	pyScript=$(cat <<-END
		import sys
		import json
		config = json.load(sys.stdin, strict=False)
		installed = config['policies']['Certificates']['Install']
		if not "$publicKeyFile" in installed:
		  installed.append("$publicKeyFile")
		if not "/etc/ssl/certs/$pemFile" in installed:
		  installed.append("/etc/ssl/certs/$pemFile")
		print(json.dumps(config))
	END
	)
	python3 -c "$pyScript"
)


__install_local_cert_debian__() (
	publicKeyFile="$1" &&
	sudo -p 'Password to install trusted certificate' \
		cp "$publicKeyFile" /usr/local/share/ca-certificates &&
	sudo update-ca-certificates
)


__clean_up_invalid_cert__() (
	commonName="$1" &&
	certName="$2" &&
	echo "Clean up certs for ${commonName} if needed"
	case $(uname) in
		(Darwin*)
			cert=''
			#turns out the d flag is not posix compliant :<
			certs_matching_name "$commonName" \
				| while read line; do
					cert=$(printf "%s\n%s" "$cert" "$line")
					if [ "$line" = '-----END CERTIFICATE-----' ]; then
						sha256Value=$(echo "$cert" | extract_sha256_from_cert) &&
						echo "$cert" | is_cert_expired &&
						sudo security delete-certificate \
							-Z "$sha256Value" -t $(__get_keychain_osx__)
						cert=''
					fi
				done
			;;
		(*)
				cert=''
				#turns out the d flag is not posix compliant :<
				scan_pems_for_common_name "$commonName" \
					| while read line; do
						cert=$(printf "%s\n%s" "$cert" "$line")
						if [ "$line" = '-----END CERTIFICATE-----' ]; then
							echo "$cert" | is_cert_expired &&
							{
								certDir='/usr/local/share/ca-certificates'
								if [ -z "$certName" ]; then
									certName="$commonName"
								fi
								sudo -p \
									"Need pass to delete from ${certDir}" \
									rm "$certDir"/"$certName"*.crt;
								sudo update-ca-certificates
							}
							cert=''
						fi
					done
			;;
	esac
	return 0
)


__setup_ssl_cert_local__() (
	commonName="$1"
	domain="$2" &&
	publicKeyFile="$3" &&
	privateKeyFile="$4" &&

	case $(uname) in
		(Darwin*)
			__openssl_gen_cert__ "$commonName" "$domain" \
				"$publicKeyFile" "$privateKeyFile" &&
			__install_local_cert_osx__ "$publicKeyFile" ||
			return 1
			;;
		(*)
			if [ -f '/etc/debian_version' ]; then
				__openssl_gen_cert__ "$commonName" "$domain" \
					"$publicKeyFile" "$privateKeyFile" &&
				__install_local_cert_debian__ "$publicKeyFile" ||
				return 1
			else
				echo "operating system not configured"
				return 1
			fi
			;;
	esac
	return 0
)


setup_ssl_cert_local_debug() (
	process_global_vars "$@" &&
	publicKeyFile=$(__get_debug_cert_path__).public.key.crt &&
	privateKeyFile=$(__get_debug_cert_path__).private.key.pem &&
	__clean_up_invalid_cert__ "${SHF_APP}-localhost"
	__setup_ssl_cert_local__ "${SHF_APP}-localhost" 'localhost' \
		"$publicKeyFile" "$privateKeyFile"
	publicKeyName=$(__get_debug_cert_name__).public.key.crt &&
	__set_firefox_cert_policy__ "$publicKeyName" &&
	setup_react_env_debug
)


print_ssl_cert_info() (
	process_global_vars "$@" &&
	domain=$(__get_domain_name__ "$SHF_ENV" 'omitPort') &&
	echo "$domain"
	case "$SHF_ENV" in
		(local*)
			isDebugServer=${1#is_debug_server=}
			if [ -n "$isDebugServer" ]; then
				domain="${domain}-localhost"
			fi
				echo "#### nginx info ####"
				echo "$(__get_local_nginx_cert_path__).public.key.crt"
				cert=''
				certs_matching_name "$domain" \
					| while read line; do
						cert=$(printf "%s\n%s" "$cert" "$line")
						if [ "$line" = '-----END CERTIFICATE-----' ]; then
							sha256Value=$(echo "$cert" | extract_sha256_from_cert) &&
							echo "$cert" | openssl x509 -enddate -subject -noout
							cert=''
						fi
					done
				echo "#### debug server info ####"
				echo "${domain}-localhost"
				echo "$(__get_debug_cert_path__).public.key.crt"
				cert=''
				certs_matching_name "${SHF_APP}-localhost" \
					| while read line; do
						cert=$(printf "%s\n%s" "$cert" "$line")
						if [ "$line" = '-----END CERTIFICATE-----' ]; then
							sha256Value=$(echo "$cert" | extract_sha256_from_cert) &&
							echo "$cert" | openssl x509 -enddate -subject -noout
							cert=''
						fi
					done
			;;
		(*)
			publicKeyFile=$(__get_remote_public_key__) &&
			cat "$publicKeyFile" | openssl x509 -enddate -subject -noout
			;;
	esac
)


add_test_url_to_hosts() (
	domain="$1"
	if [ -z "$domain" ]; then
		echo "Missing domain in adding to hosts"
		return 1
	fi
	if ! grep "$domain" /etc/hosts >/dev/null; then
		sudo -p 'password to update hosts' \
			sh -c "printf '127.0.0.1\t${domain}\n' >> /etc/hosts"
	fi
)


setup_ssl_cert_nginx() (
	process_global_vars "$@" &&
	domain=$(__get_domain_name__ "$SHF_ENV" 'omitPort') &&
	case "$SHF_ENV" in
		(local*)
			add_test_url_to_hosts "$domain"
			publicKeyFile=$(__get_local_nginx_cert_path__).public.key.crt &&
			privateKeyFile=$(__get_local_nginx_cert_path__).private.key.pem &&

			# we're leaving off the && because what would that even mean here?
			__clean_up_invalid_cert__ "$domain" $(__get_local_nginx_cert_name__)
			if [ -z $(__certs_matching_name_exact__ "$domain") ]; then
				__setup_ssl_cert_local__ \
				"$domain" "$domain" "$publicKeyFile" "$privateKeyFile"
			fi
			publicKeyName=$(__get_local_nginx_cert_name__).public.key.crt &&
			__set_firefox_cert_policy__ "$publicKeyName"
			;;
		(*)
			publicKeyFile=$(__get_remote_public_key__) &&
			privateKeyFile=$(__get_remote_private_key__) &&
			intermediateKeyFile=$(__get_remote_intermediate_key__) &&

			if [ ! -e "$publicKeyFile" ] || [ ! -e "$privateKeyFile" ] ||
			cat "$publicKeyFile" | is_cert_expired ||
			str_contains "$__REPLACE__" "ssl_certs"; then
				echo "downloading new certs"
				sslVars=$(get_ssl_vars)
				echo "$sslVars" | stdin_json_extract_value 'privatekey' | \
				perl -pe 'chomp if eof' > "$privateKeyFile" &&
				echo "$sslVars" | \
				stdin_json_extract_value 'certificatechain' | \
				perl -pe 'chomp if eof' > "$publicKeyFile" &&
				echo "$sslVars" | \
				stdin_json_extract_value 'intermediatecertificate' | \
				perl -pe 'chomp if eof' > "$intermediateKeyFile"
			fi
			;;
	esac
	echo "Done setting up certificates for ${domain}"
)




get_nginx_value() (
	key=${1:-'conf-path'}
	#break options into a list
	#then isolate the option we're interested in
	sudo -p "Need pass to get nginx values " \
		nginx -V 2>&1 | \
		sed 's/ /\n/g' | \
		sed -n "/--${key}/p" | \
		sed 's/.*=\(.*\)/\1/' ||
		show_err_and_return "."
)


get_nginx_conf_dir_include() (
	nginxConf=$(get_nginx_value) &&
	guesses=$(cat<<-'EOF'
		include /etc/nginx/sites-enabled/*;
		include servers/*;
	EOF
	) &&
	#determine which one of these locations is referenced in the nginx config
	echo "$guesses" | while read guess; do
		if grep -F "$guess" "$nginxConf" >/dev/null; then
			echo "$guess"
			break
		fi
	done ||
	echo "failed while searching for nginx config"
)


__copy_and_update_nginx_template__() {
	sudo -p 'copy nginx config' \
		cp "$SHF_TEMPLATES_SRC"/nginx_template.conf "$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e \
			"s@<SHF_CLIENT_DEST>@$(get_web_root)/${SHF_CLIENT_DEST}@" \
			"$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e "s@<SHF_SERVER_NAME>@${SHF_SERVER_NAME}@g" "$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e "s@<SHF_API_PORT>@${SHF_API_PORT}@" "$appConfFile"
}


__set_local_nginx_app_conf__() {
	publicKey=$(__get_local_nginx_cert_path__).public.key.crt &&
	privateKey=$(__get_local_nginx_cert_path__).private.key.pem &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e "s/<listen>/8080 ssl/" "$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e "s@<ssl_public_key>@${publicKey}@" \
		"$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e "s@<ssl_private_key>@${privateKey}@" \
		"$appConfFile"
}


__set_deployed_nginx_app_conf__() {
	sudo -p "update ${appConfFile}" \
		perl -pi -e "s/<listen>/[::]:443 ssl/" "$appConfFile" &&

		sudo -p "update ${appConfFile}" \
		perl -pi -e \
		"s@<ssl_public_key>@$(__get_remote_public_key__)@" \
		"$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e \
		"s@<ssl_private_key>@$(__get_remote_private_key__)@" \
		"$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e \
		"s@<ssl_intermediate>@$(__get_remote_intermediate_key__)@" \
		"$appConfFile" &&
	sudo -p "update ${appConfFile}" \
		perl -pi -e \
		's/#ssl_trusted_certificate/ssl_trusted_certificate/' \
		"$appConfFile"
}


update_nginx_conf() (
	echo 'updating nginx site conf'
	appConfFile="$1"
	error_check_all_paths "$SHF_TEMPLATES_SRC" "$appConfFile" &&
	__copy_and_update_nginx_template__ &&
	case "$SHF_ENV" in
		(local*)
			__set_local_nginx_app_conf__
			;;
		(*)
			__set_deployed_nginx_app_conf__
			;;
	esac &&
	echo 'done updating nginx site conf'
)


get_abs_path_from_nginx_include() (
	confDirInclude="$1"
	confDir=$(echo "$confDirInclude" | sed 's/include *//' | \
		sed 's@/\*; *@@')
	#test if already exists as absolute path
	if [ -d "$confDir" ]; then
		echo "$confDir"
		return
	else
		configPath=$(get_nginx_value) ||
		show_err_and_return "Had issues pulling nginx config path"
		sitesFolderPath=$(dirname "$configPath")
		echo "sitesFolderPath: ${sitesFolderPath}" >&2
		absPath="$sitesFolderPath"/"$confDir"
		if [ ! -d "$absPath" ]; then
			if [ -e "$absPath" ]; then
				echo "{$absPath} is a file, not a directory" 1>&2
				return 1
			fi
			#Apparently nginx will look for includes with either an absolute path
			#or path relative to the config
			#some os'es are finicky about creating directories at the root lvl
			#even with sudo, so we're not going to even try
			#we'll just create missing dir in $sitesFolderPath folder
			sudo -p "Add nginx conf dir" \
				mkdir -pv "$absPath"
		fi
		echo "$absPath"
	fi
)


get_nginx_conf_dir_abs_path() (
	confDirInclude=$(get_nginx_conf_dir_include)
	get_abs_path_from_nginx_include "$confDirInclude"
)


enable_nginx_include() (
	echo 'enabling nginx site confs'
	confDirInclude="$1"
	escapedGuess=$(literal_to_regex "$confDirInclude")
	configPath=$(get_nginx_value) &&
	#uncomment line if necessary in config
	sudo -p "Enable ${confDirInclude}" \
		perl -pi -e "s/^[ \t]*#// if m@$escapedGuess@" "$configPath" &&
	echo 'done enabling nginx site confs' ||
	echo 'failed to enable nginx site confs'
)


restart_nginx() (
	echo 'starting/restarting up nginx'
	case $(uname) in
		(Darwin*)
			nginx -s reload
			;;
		(Linux*)
			if systemctl is-active --quiet nginx; then
				sudo -p 'starting nginx' systemctl restart nginx
			else
				sudo -p 'enabling nginx' systemctl enable nginx
				sudo -p 'restarting nginx' systemctl start nginx
			fi
			;;
		(*) ;;
	esac &&
	echo 'Done starting/restarting up nginx'
)


refresh_certs() (
	setup_ssl_cert_nginx &&
	restart_nginx
)


print_nginx_conf_location() (
	process_global_vars "$@" >/dev/null &&
	confDirInclude=$(get_nginx_conf_dir_include) &&
	confDir=$(get_abs_path_from_nginx_include "$confDirInclude") 2>/dev/null
	echo "$confDir"/"$SHF".conf
)


print_cert_paths() (
	process_global_vars "$@" >/dev/null &&
	confDirInclude=$(get_nginx_conf_dir_include) &&
	confDir=$(get_abs_path_from_nginx_include "$confDirInclude") 2>/dev/null
	cat "$confDir"/"$SHF_APP".conf | perl -ne \
	'print "$1\n" if /ssl_certificate ([^;]+)/'
	cat "$confDir"/"$SHF_APP".conf | perl -ne \
	'print "$1\n" if /ssl_certificate_key ([^;]+)/'
	cat "$confDir"/"$SHF_APP".conf | perl -ne \
	'print "$1\n" if /[^#]ssl_trusted_certificate ([^;]+)/'
)


setup_nginx_confs() (
	echo 'setting up nginx confs'
	process_global_vars "$@" &&
	confDirInclude=$(get_nginx_conf_dir_include) &&
	#remove trailing path chars
	confDir=$(get_abs_path_from_nginx_include "$confDirInclude") &&
	setup_ssl_cert_nginx &&
	enable_nginx_include "$confDirInclude" &&
	update_nginx_conf "$confDir"/"$SHF_APP".conf &&
	sudo -p 'Remove default nginx config' \
		rm -f "$confDir"/default &&
	restart_nginx &&
	echo "done setting up nginx confs at ${confDir}"
)


show_current_py_lib_files() (
	process_global_vars "$@" >/dev/null 2>&1 &&
	set_python_version_const >/dev/null 2>&1 &&
	envDir="lib/python${pyMajor}.${pyMinor}/site-packages/${SHF_LIB}"
	echo "$(__get_app_root__)"/"$SHF_TRUNK"/"$SHF_PY_ENV"/"$envDir"
)


show_web_py_files() (
	process_global_vars "$@" >/dev/null 2>&1 &&
	echo "$(get_web_root)"/"$SHF_API_DEST"
)


__get_remote_export_script__() (
	if [ -n "$1" ]; then
		exportMod='export'
	else
		exportMod=''
	fi
	output="export expName='${expName}';"
	output="${output} export PB_SECRET='$(__get_pb_secret__)';" &&
	output="${output} export PB_API_KEY='$(__get_pb_api_key__)';" &&
	output="${output} export SHF_DB_PASS_OWNER='$(__get_db_owner_key__)';" &&
	echo "$output"
)


startup_api() (
	set_env_vars "$@" &&
	if ! str_contains "$__SKIP__" "setup_api"; then
		setup_api
	fi &&
	echo "Server base is $(pwd). Look there for api.out and the log file"
	echo "done starting up api. Access at ${SHF_FULL_URL}" ||
	echo "failed while trying to start up api"
)


startup_nginx_for_debug() (
	process_global_vars "$@" &&
	export SHF_API_PORT='8032'
	setup_nginx_confs &&
	restart_nginx
)


setup_api() (
	echo "setting up api"
	process_global_vars "$@" &&
	sync_utility_scripts &&
	setup_nginx_confs &&
	echo "done setting up api"
)


create_swap_if_needed() (
		case $(uname) in
		(Linux*)
			if [ ! -e /swapfile ]; then
				sudo dd if=/dev/zero of=/swapfile bs=128M count=24 &&
				sudo chmod 600 /swapfile &&
				sudo mkswap /swapfile &&
				sudo swapon /swapfile
			fi
			;;
		(*) ;;
	esac
)


setup_client() (
	echo "setting up client"
	process_global_vars "$@" &&
	error_check_all_paths "$SHF_CLIENT_SRC" \
		"$(get_web_root)"/"$SHF_CLIENT_DEST" &&
	#copy built code to new location
	sudo -p 'Pass required to copy client files: ' \
		cp -rv "$SHF_CLIENT_SRC"/build/. \
			"$(get_web_root)"/"$SHF_CLIENT_DEST" &&
	unroot_dir "$(get_web_root)"/"$SHF_CLIENT_DEST" &&
	echo "done setting up client"
)


setup_full_web() (
	echo "setting up full web"
	process_global_vars "$@" &&
	setup_client &&
	setup_api &&
	echo "done setting up full web."
)


startup_full_web() (
	echo "starting up full web"
	process_global_vars "$@" &&
	setup_client &&
	startup_api &&
	echo "done starting up full web. Access at ${SHF_FULL_URL}"
)


__create_fake_keys_file__() {
	echo "shf_auth_key=$(openssl rand -hex 32)" \
		> "$(__get_app_root__)"/keys/"$SHF_PROJ_NAME_SNAKE"
}


get_hash_of_file() (
	file="$1"
	pyScript=$(cat <<-END
		import sys, hashlib
		print(hashlib.md5(sys.stdin.read().encode("utf-8")).hexdigest())
	END
	)
	cat "$file" | python3 -c "$pyScript"
)




replace_sql_script() (
	process_global_vars "$@" &&
	setup_app_directories
	copy_dir "$SHF_SQL_SCRIPTS_SRC" "$(__get_app_root__)"/"$SHF_SQL_SCRIPTS_DEST"
)


#assume install_setup.sh has been run
setup_unit_test_env() (
	echo 'setting up test environment'
	process_global_args "$@" || return

	define_consts &&
	define_directory_vars &&
	export __TEST_FLAG__='true'
	publicKeyFile=$(__get_debug_cert_path__).public.key.crt

	__create_fake_keys_file__
	setup_app_directories

	echo "done setting up test environment"
)


#assume install_setup.sh has been run
run_unit_tests() (
	echo "running unit tests"
	process_global_vars "$@"
	export __TEST_FLAG__='true'
	setup_unit_test_env >/dev/null &&
	echo "done running unit tests"
)


debug_print() (
	msg="$1"
	if [ -n "$__DIAG_FLAG__" ]; then
		echo "$msg" >> diag_out_"$__INCLUDE_COUNT__"
	fi
)


get_rc_candidate() {
	case $(uname) in
		(Linux*)
			echo "$HOME"/.bashrc
			;;
		(Darwin*)
			echo "$HOME"/.zshrc
			;;
		(*) ;;
	esac
}


__get_app_root__() (
	if [ -n "$__TEST_FLAG__" ]; then
		echo "$SHF_TEST_ROOT"
		return
	fi
	echo "$SHF_APP_ROOT"
)


get_web_root() (
	if [ -n "$__TEST_FLAG__" ]; then
		echo "$SHF_TEST_ROOT"
		return
	fi
	case $(uname) in
		(Linux*)
			echo "${SHF_WEB_ROOT_OVERRIDE:-/srv}"
			return
			;;
		(Darwin*)
			echo "${SHF_WEB_ROOT_OVERRIDE:-/Library/WebServer}"
			return
			;;
		(*) ;;
	esac
)


connect_remote() (
	process_global_vars "$@" &&
	echo "connectiong to $(__get_address__) using $(__get_id_file__)" &&
	ssh -ti $(__get_id_file__) "root@$(__get_address__)" \
		$(__get_remote_export_script__) bash -l
)

print_exported_env_vars() (
	process_global_vars "$@" &&
	echo "App root: $(__get_app_root__)"
	__get_remote_export_script__ "$@"
)

connect_sftp() (
	process_global_vars "$@" >&2 &&
	sftp -6 -i $(__get_id_file__) "root@[$(__get_address__)]"
)


process_global_args() {
	#in case need to pass the args to a remote script. example
	__GLOBAL_ARGS__=''
	while [ ! -z "$1" ]; do
		case "$1" in
			#build out to test_trash rather than the normal directories
			#sets SHF_APP_ROOT and SHF_WEB_ROOT_OVERRIDE
			#without having to set them explicitly
			(test)
				export __TEST_FLAG__='true'
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} test"
				;;
			(replace=*)
				export __REPLACE__=${1#replace=}
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} replace='${__REPLACE__}'"
				;;
			(clean) #tells setup functions to delete files/dirs before installing
				export __CLEAN_FLAG='clean'
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} clean"
				;;
			#activates debug_print. Also tells deploy script to use the diag branch
			(diag)
				export __DIAG_FLAG__='true'
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} diag"
				echo '' > diag_out_"$__INCLUDE_COUNT__"
				;;
			(setuplvl=*) #affects which setup scripst to run
				export __SETUP_LVL__=${1#setuplvl=}
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} setuplvl='${__SETUP_LVL__}'"
				;;
			#when I want to conditionally run with some experimental code
			(experiment=*)
				export __EXPERIMENT_NAME__=${1#experiment=}
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} experiment='${__EXPERIMENT_NAME__}'"
				;;
			(skip=*)
				export __SKIP__=${1#skip=}
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} skip='${__SKIP__}'"
				;;
			(dbsetuppass=*)
				export __DB_SETUP_PASS__=${1#dbsetuppass=}
				__GLOBAL_ARGS__="${__GLOBAL_ARGS__} dbsetuppass='${__DB_SETUP_PASS__}'"
				;;
			(*) ;;
		esac
		shift
	done
	export __GLOBAL_ARGS__
}


define_consts() {
	[ -z "$__SHF_CONSTANTS_SET__" ] || return 0
	export SHF_PACMAN='pacman'
	export SHF_APT_CONST='apt-get'
	export SHF_HOMEBREW='homebrew'
	export SHF_CURRENT_USER=$(whoami)
	export SHF_PROJ_NAME_0='Space Habit Frontier'
	export SHF_PROJ_NAME_SNAKE='space_habit_frontier'
	export SHF_BUILD_DIR='builds'
	export SHF_CONTENT_DIR='content'
	export SHF_BIN_DIR='.local/bin'
	export SHF_API_PORT='8033'
	#python environment names
	export SHF_PY_ENV='shf_env'

	export SHF_APP_ROOT=${SHF_APP_ROOT:-"$HOME"}
	export SHF_TRUNK="$SHF_PROJ_NAME_SNAKE"
	export SHF_LIB="$SHF_PROJ_NAME_SNAKE"
	export SHF_DEV_OPS_LIB="$SHF_PROJ_NAME_SNAKE"_dev_ops
	export SHF_APP="$SHF_PROJ_NAME_SNAKE"

	export SHF_CONFIG_DIR="$SHF_TRUNK"/config
	export SHF_DB_DIR="$SHF_TRUNK"/db
	export SHF_UTEST_ENV_DIR="$SHF_TEST_ROOT"/utest

	# directories that should be cleaned upon changes
	# suffixed with DEST
	export SHF_TEMPLATES_DEST="$SHF_TRUNK"/templates
	export SHF_SQL_SCRIPTS_DEST="$SHF_TRUNK"/sql_scripts
	export SHF_API_DEST=api/"$SHF_APP"
	export SHF_CLIENT_DEST=client/"$SHF_APP"


	export SHF_SERVER_NAME=$(__get_domain_name__ "$SHF_ENV")
	export SHF_FULL_URL="https://${SHF_SERVER_NAME}"

	export __SHF_CONSTANTS_SET__='true'
	echo "constants defined"
}


create_install_directory() {
	if [ -z "$SHF_LOCAL_REPO_DIR" ]; then
		echo 'SHF_LOCAL_REPO_DIR is set. '
		echo 'create_install_directory may have been run out of sequence'
		exit 1
	fi
	[ -d "$SHF_LOCAL_REPO_DIR" ] ||
	mkdir -pv "$SHF_LOCAL_REPO_DIR"
}

__get_url_base__() (
	echo 'spacehabitfrontier'
)


__get_domain_name__() (
	envArg="$1"
	omitPort="$2"
	urlBase=$(__get_url_base__)
	tld=''
	if [ -z "$tld" ]; then
		echo "tld has been setup for this app yet" >&2
		echo ""
	fi
	case "$envArg" in
		(local*)
			if [ -n "$omitPort" ]; then
				urlSuffix="-local.${tld}"
			else
				urlSuffix="-local.${tld}:8080"
			fi
			;;
		(*)
			urlSuffix=".${tld}"
			;;
	esac
	echo "${urlBase}${urlSuffix}"
)



define_repo_paths() {
	export SHF_SRC="$(get_repo_path)/src"
	export SHF_API_SRC="$SHF_SRC/api"
	export SHF_CLIENT_SRC="$SHF_SRC/client"
	export SHF_LIB_SRC="$SHF_SRC/$SHF_LIB"
	export SHF_DEV_OPS_LIB_SRC="$SHF_SRC/$SHF_DEV_OPS_LIB"
	export SHF_TEMPLATES_SRC="$(get_repo_path)/templates"
	export SHF_SQL_SCRIPTS_SRC="$(get_repo_path)/sql_scripts"
	export SHF_REFERENCE_SRC="$(get_repo_path)/reference"
	export SHF_TEST_ROOT="$(get_repo_path)/test_trash"
	echo "source paths defined"
}


setup_app_directories() {
	[ -e "$(__get_app_root__)"/"$SHF_TRUNK" ] ||
	mkdir -pv "$(__get_app_root__)"/"$SHF_TRUNK"
	[ -e "$(__get_app_root__)"/"$SHF_CONFIG_DIR" ] ||
	mkdir -pv "$(__get_app_root__)"/"$SHF_CONFIG_DIR"
	[ -e "$(__get_app_root__)"/"$SHF_DB_DIR" ] ||
	mkdir -pv "$(__get_app_root__)"/"$SHF_DB_DIR"
	[ -e "$(__get_app_root__)"/keys ] ||
	mkdir -pv "$(__get_app_root__)"/keys
	[ -e "$(__get_app_root__)"/"$SHF_BUILD_DIR" ] ||
	mkdir -pv "$(__get_app_root__)"/"$SHF_BUILD_DIR"
	[ -e "$(__get_app_root__)"/"$SHF_CONTENT_DIR" ] ||
	mkdir -pv "$(__get_app_root__)"/"$SHF_CONTENT_DIR"
}


__setup_api_dir__() {
	if [ !  -e "$(get_web_root)"/"$SHF_API_DEST" ]; then

		sudo -p 'Pass required to create web server directory: ' \
			mkdir -pv "$(get_web_root)"/"$SHF_API_DEST" ||
		show_err_and_return "Could not create $(get_web_root)/${SHF_API_DEST}"
	fi
}


define_directory_vars() {
	[ -z "$__DIRECTORY_VARS_SET__" ] || return 0
	export SHF_LOCAL_REPO_DIR=$(get_repo_path) &&
	define_repo_paths
	export __DIRECTORY_VARS_SET__='true'
}


process_global_vars() {
	process_global_args "$@" || return
	[ -z "$__GLOBALS_SET__" ] || return 0

	define_consts &&
	define_directory_vars &&
	setup_app_directories &&

	export __GLOBALS_SET__='globals'
}


unset_globals() {
	enable_wordsplitting
	exceptions=$(tr '\n' ' '<<-'EOF'
		SHF_ENV
		SHF_AUTH_SECRET_KEY
		SHF_DB_PASS_API
		SHF_DB_PASS_OWNER
		SHF_LOCAL_REPO_DIR
		SHF_REPO_URL
		SHF_SERVER_KEY_FILE
		SHF_SERVER_SSH_ADDRESS
		__DB_SETUP_PASS__
	EOF
	)
	cat "$(get_repo_path)"/shf_dev_ops.sh | grep export \
		| sed -n -e 's/^\t*export \([a-zA-Z0-9_]\{1,\}\)=.*/\1/p' | sort -u \
		| while read constant; do
				#exceptions is unquoted on purpose
				if array_contains "$constant" $exceptions; then
					echo "leaving $constant"
					continue
				fi
				case "$constant" in
					(SHF_*)
						echo "unsetting ${constant}"
						unset "$constant"
						;;
					(__*)
						echo "unsetting ${constant}"
						unset "$constant"
						;;
					(*)
						;;
					esac
			done
	disable_wordsplitting
}


fn_ls() (
	process_global_vars "$@" >/dev/null
	perl -ne 'print "$1\n" if /^([a-zA-Z_0-9]+)\(\)/' \
		"$(get_repo_path)"/shf_dev_ops.sh | sort
)

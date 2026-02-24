#!/bin/sh


meName="shf_dev_ops.sh"
isSourced=0
[ "${0##*/}" != "${meName}" ] && isSourced=1

deploy_app() (
	ansible-playbook dev_ops/deploy_api.yml -i ~/.ansible/inventories/space_habit_frontier  --ask-vault-pass -K
)


setup_new_box_vm() (
	ansible-playbook dev_ops/new_box.yml -i ~/.ansible/inventories/vms_inv  --ask-vault-pass -K
)


setup_new_box() (
	ansible-playbook dev_ops/new_box.yml -i ~/.ansible/inventories/space_habit_frontier  --ask-vault-pass -K
)


deploy_app_vm() (
	ansible-playbook dev_ops/deploy_api.yml -i ~/.ansible/inventories/vms_inv  --ask-vault-pass -K
)


setup_schedules_vm() (
	ansible-playbook dev_ops/setup_schedules.yml -i ~/.ansible/inventories/vms_inv  --ask-vault-pass
)


deploy_local_app() (
	ansible-playbook dev_ops/startup_api.yml -i ~/.ansible/inventories/testing  --ask-vault-pass -K
)


update_db() (
	ansible-playbook dev_ops/update_db.yml --skip-tags  entitled
)


setup_db() (
	ansible-playbook dev_ops/update_db.yml -i ~/.ansible/inventories/testing -K
)

setup_schedules() (
	ansible-playbook dev_ops/run_schedule_setup.yml -i ~/.ansible/inventories/space_habit_frontier  --ask-vault-pass -K
)


replace_local_db() (
	ansible-playbook dev_ops/replace_local_db.yml -i ~/.ansible/inventories/testing --ask-vault-pass -K
)


# add_migration() (

# )


# blank_migration() (

# )




# gen_sql() (
# 	cd ..
# 	alembic_exe='test_trash/musical_chairs/mcr_env/bin/alembic'
# 	"$alembic_exe" upgrade head --sql
# )


if [ $isSourced = 0 ]; then
	command="$1"
	shift
	"$command" "$@"
fi